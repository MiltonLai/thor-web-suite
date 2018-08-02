<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
  <meta name="apple-mobile-web-app-capable" content="yes"/>
  <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
  <meta name="format-detection" content="telephone=no"/>
  <#if se_meta_extra??><#list se_meta_extra as extra>${extra}</#list></#if>
  <title>${se_data.title}</title>
  <link href="${sys_config.resource_path}/weui/weui.min.css?${sys_config.build_timestamp}" rel="stylesheet" type="text/css">
  <link href="${sys_config.resource_path}/assets/css/style.css?${sys_config.build_timestamp}" rel="stylesheet" type="text/css">
  <#if se_meta_css??><#list se_meta_css as css><link rel="stylesheet" href="${sys_config.resource_path}${css}" type="text/css" media="screen, projection">
  </#list></#if>
  <script src="${sys_config.resource_path}/zepto/zepto.1.2.0.min.js"></script>

  <script>
    var sys_config = {
      site_base: '${sys_config.site_base}',
      root_path: '${sys_config.root_path}',
      resource_path: '${sys_config.resource_path}'
    };
    // Baidu Tongji
    var _hmt = _hmt || [];
    (function () {
      var hm = document.createElement("script");
      hm.src = "//hm.baidu.com/hm.js?ed3e094839b31e6ec3769e582510bb8a";
      var s = document.getElementsByTagName("script")[0];
      s.parentNode.insertBefore(hm, s);
    })();
  </script>

</head>
<body>
<div id="loadingToast" class="weui_loading_toast">
  <div class="weui_mask_transparent"></div>
  <div class="weui_toast">
    <div class="weui_loading" style="top:48%"> <!-- :) -->
      <div class="weui_loading_leaf weui_loading_leaf_0"></div>
      <div class="weui_loading_leaf weui_loading_leaf_1"></div>
      <div class="weui_loading_leaf weui_loading_leaf_2"></div>
      <div class="weui_loading_leaf weui_loading_leaf_3"></div>
      <div class="weui_loading_leaf weui_loading_leaf_4"></div>
      <div class="weui_loading_leaf weui_loading_leaf_5"></div>
      <div class="weui_loading_leaf weui_loading_leaf_6"></div>
      <div class="weui_loading_leaf weui_loading_leaf_7"></div>
      <div class="weui_loading_leaf weui_loading_leaf_8"></div>
      <div class="weui_loading_leaf weui_loading_leaf_9"></div>
      <div class="weui_loading_leaf weui_loading_leaf_10"></div>
      <div class="weui_loading_leaf weui_loading_leaf_11"></div>
    </div>
  </div>
</div>