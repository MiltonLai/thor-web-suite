  <!-- Static navbar -->
  <nav class="navbar navbar-default navbar-static-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${sys_config.root_path}">Demo</a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <#if se_session?? && se_session.userId != "0">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">欢迎您, ${se_user.name}<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="${sys_config.root_path}/logout.html">退出</a></li>
              </ul>
            </li>
          </#if>
        </ul>
      </div>
      <!--/.nav-collapse -->
    </div>
  </nav>