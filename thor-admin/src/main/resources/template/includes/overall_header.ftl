<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <#if se_meta_extra??><#list se_meta_extra as extra>${extra}</#list></#if>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <title>Demo<#if se_data.title??> | ${se_data.title}</#if></title>
  <!-- Bootstrap -->
  <link href="${sys_config.resource_path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${sys_config.resource_path}/bootstrap-notify/css/bootstrap-notify.css" rel="stylesheet">
  <link href="${sys_config.resource_path}/assets/css/main.css" rel="stylesheet">
  <#if se_meta_css??><#list se_meta_css as css><link rel="stylesheet" href="${sys_config.resource_path}${css}" type="text/css" media="screen, projection"></#list></#if>
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via utilities:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="${sys_config.resource_path}/jquery/1.11.3/jquery.min.js"></script>

  <script>

    var sys_config = {
      site_base: '${sys_config.site_base}',
      root_path: '${sys_config.root_path}',
      resource_path: '${sys_config.resource_path}'
    };

  </script>
</head>
<body>