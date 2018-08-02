
  <script>
    $(function () {
      $("#loadingToast").hide();

      // 延迟CNZZ统计, 以免影响页面载入
      var cz = document.createElement("script");
      cz.src = "//s11.cnzz.com/z_stat.php?id=1259742595&web_id=1259742595";
      document.getElementsByTagName('body')[0].appendChild(cz);
    });

  </script>
  <script src="${sys_config.resource_path}/assets/js/common.js"></script>
  <#if se_meta_js??><#list se_meta_js as js><script src="${sys_config.resource_path}${js}"></script></#list></#if>
</body>
</html>
