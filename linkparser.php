<?php
/**
 * Created by PhpStorm.
 * User: lcssgml
 * Date: 1/6/17
 * Time: 11:28 PM
 */

$html = file_get_contents('http://dexonline.net/dictionar-sinonime/litera/t');
//Create a new DOM document
$dom = new DOMDocument;
//var_dump($html);
//Parse the HTML. The @ is used to suppress any parsing errors
//that will be thrown if the $html string isn't valid XHTML.
@$dom->loadHTML($html);

//Get all links. You could also use any other tag name here,
//like 'img' or 'table', to extract other tags.
$links = $dom->getElementsByTagName('a');

//$sp = fopen("link_c.txt","a");
$content='';

//Iterate over the extracted links and display their URLs
foreach ($links as $link){
    //Extract and show the "href" attribute.
    //echo $link->nodeValue;
    $var=$link->getAttribute('href');
    //echo $var;
    if(strpos($var,'sinonime-')){
        //echo $link->getAttribute('href'), '<br>';
        $content .=$var.PHP_EOL;
    }
}
if(file_put_contents('links/link_ss.txt',$content,FILE_APPEND)){
    echo 'link parse done';
}else{
    echo 'error';
}

