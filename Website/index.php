<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Total Control Remote Control</title>
<link href="./css/layout.css" rel="stylesheet" type="text/css" />



<?php

if (isset($_GET[remote])){
	$remote = $_GET[remote];
	echo "<script> var m_remote = '", $remote,"'; </script>";
    if (isset($_GET[code])) {
        
        $code   = $_GET[code];

        $fd = fsockopen ('unix:///dev/lircd', $errorno, $errorstr);

        fwrite ($fd, "send_once $remote $code\n" );
            
    }
	
} else {
	echo "<script> var m_remote = null; </script>";
}



?>

<script>
 
function send(code)
{
	

    var url = "<?php echo $_SERVER[PHP_SELF]?>?remote=" + m_remote 
	+ "&code=" + code;

    window.open (url, "_self");
}


</script>


</head>

<body>
<table align="center" cellpadding="5px">
  <tr align="center">
    <td></td>
    <td><button type="button" onclick="m_remote = 'Apple'" class="choose_button">Apple</button></td>
    <td></td>
    <td><button type="button" onclick="m_remote = 'Samsung'" class="choose_button">Samsung</button></td>
    <td></td>
  <tr align="center">
    <td><button type="button" onclick="send('key_source')" class="word_button">Source</button></td>
    <td></td>
    <td><button type="button" onclick="send('key_menu')" class="word_button">Menu</button></td>
    <td></td>
    <td><button type="button" onclick="send('key_power')"
                class="power_button">Power</button></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr align="center">
    <td><button type="button" onclick="send('key_mute')" class="word_button">Mute</button></td>
    <td></td>
    <td><button type="button" onclick="send('key_up')" class="vert_arrow_button">&uarr;</button></td>
    <td></td>
    <td><button type="button" onclick="send('key_sleep')"class="word_button">Sleep</button></td>
  </tr>
  <tr align="center">
    <td></td>
    <td><button type="button" onclick="send('key_left')" class="horz_arrow_button">&larr;</button></td>
    <td><button type="button" onclick="send('key_enter')" class="select_button">Select</button></td>
    <td><button type="button" onclick="send('key_right')" class="horz_arrow_button">&rarr;</button></td>
    <td></td>
  </tr>
  <tr align="center">
    <td><button type="button" onclick="send('key_return')" class="word_button">Return</button></td>
    <td></td>
    <td><button type="button" onclick="send('key_down')" class="vert_arrow_button">&darr;</button></td>
    <td></td>
    <td><button type="button" onclick="send('key_exit')" class="word_button">Exit</button></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr align="center">
    <td></td>
    <td></td>
    <td><button onclick="send('key_play')"type="button">&#9658;</button></td>
    <td></td>
    <td></td>
  </tr>
  <tr align="center">
    <td></td>
    <td><button type="button" onclick="send ('key_vol_up')"
            class="up_button">Vol +</button></td>
    <td></td>
    <td><button type="button" onclick="send ('key_ch_up')"
class="up_button">Ch +</button></td>
    <td></td>
  </tr>
  <tr align="center">
    <td></td>
    <td><button type="button" onclick="send ('key_vol_down')" class="down_button">Vol -</button></td>
    <td></td>
    <td><button type="button" onclick="send ('key_ch_down')" class="down_button">Ch -</button></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr align="center">
    <td></td>
    <td><button type="button" onclick="send('key_1')"class="num_button">1</button></td>
    <td><button type="button" onclick="send('key_2')"class="num_button">2</button></td>
    <td><button type="button" onclick="send('key_3')"class="num_button">3</button></td>
    <td></td>
  </tr>
  <tr align="center">
    <td></td>
    <td><button type="button" onclick="send('key_4')"class="num_button">4</button></td>
    <td><button type="button" onclick="send('key_5')"class="num_button">5</button></td>
    <td><button type="button" onclick="send('key_6')"class="num_button">6</button></td>
    <td></td>
  </tr>
  <tr align="center">
    <td></td>
    <td><button type="button" onclick="send('key_7')"class="num_button">7</button></td>
    <td><button type="button" onclick="send('key_8')"class="num_button">8</button></td>
    <td><button type="button" onclick="send('key_9')"class="num_button">9</button></td>
    <td></td>
  </tr>
  <tr align="center">
    <td></td>
    <td></td>
    <td><button type="button" onclick="send('key_0')"class="num_button">0</button></td>
    <td></td>
    <td></td>
  </tr>
</table>
</body>
</html>
