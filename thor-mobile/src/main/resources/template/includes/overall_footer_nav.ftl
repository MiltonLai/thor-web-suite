<div id="footer-nav" class="footer-nav ">
  <div class="col-12-3 l">
    <a class="text-center" href="${sys_config.root_path}/index.html">
      <p>首页</p>
    </a>
  </div>
    <div class="col-12-3 l">
    <a class="text-center" href="${sys_config.root_path}/product.html">
      <p>投资</p>
    </a>
  </div>
  <div class="col-12-3 l">
    <a class="text-center" href="${sys_config.root_path}/download.html">
      <p>下载</p>
    </a>
  </div>
  <div class="col-12-3 l">
    <#if se_user.isAnonymous>
    <a class="text-center" href="${sys_config.root_path}/login.html">
      <div class=" icon_user"></div>
      <p>登录/注册</p>
    </a>
    <#else>
    <a class="text-center" href="${sys_config.root_path}/account/">
      <div class=" icon_user"></div>
      <p>我的账户</p>
    </a>
    </#if>
  </div>

</div>