<?php
/**
 * Created by PhpStorm.
 * User: lcssgml
 * Date: 1/7/17
 * Time: 12:01 AM
 */
require 'simple_html_dom.php';

//Download webpage with CURL
function dlPage($href) {

    $curl = curl_init();
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($curl, CURLOPT_URL, $href);
    curl_setopt($curl, CURLOPT_REFERER, $href);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, TRUE);
    curl_setopt($curl, CURLOPT_USERAGENT, "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4");
    $str = curl_exec($curl);
    curl_close($curl);

    // Create a DOM object
    $dom = new simple_html_dom();
    // Load HTML from a string
    $dom->load($str);

    return $dom;
}

// Open the file
$fp = @fopen('links/link_d.txt', 'r');
$array=array();
// Add each line to an array

$sp = fopen("sinonime/litera_d.txt","wb");
$content='';

if ($fp) {
    $array = explode("\n", fread($fp, filesize('links/link_d.txt')));
}
$counter=1;
foreach ($array as $link) {
    //echo $link . '<br>';
    $data = dlPage($link);
    $search_test = $data->find('div[class=tip-definitie]');

    foreach ($search_test as $se) {
        $a = $se->find('a');
        foreach ($a as $b) {
            $first=substr($link,30).' ';
            $second=$b->plaintext;
            $content .=$first.': '.$second.PHP_EOL;
            //echo $content;
        }
    }
    echo $counter.' '.$link.PHP_EOL;
    $counter++;
}
var_dump($content);
fwrite($sp,$content);
fclose($sp);
