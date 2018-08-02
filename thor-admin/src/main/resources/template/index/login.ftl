<#include "includes/overall_header.ftl">

<div class="container">

  <form class="form-signin" id="login_form" method="post">
    <h2 class="form-signin-heading">ADMIN LOGIN</h2>
    <label for="inputEmail" class="sr-only">Email</label>
    <input type="email" id="inputEmail" name="inputEmail" class="form-control" placeholder="用户名/手机号/Email" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="用户密码" required>
    <div class="checkbox">
      <label>
        <input type="checkbox" value="1" name="autologin"> 自动登录
      </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
  </form>

  <script type="text/javascript">
    $("button:submit").click(function(){
      $.ajax({
        type : 'POST',
        url : '${sys_config.root_path}/login.html',
        cache:false,
        data : $('#login_form').serialize(),
        dataType : "json"
      }).done(function(data) {
        if (data.success) {
          $("#bs-notify").notify({
            message: { text: data.success },
            fadeOut: { enabled: true, delay: 500 },
            onClosed: function(){window.location = data.field;}
          }).show();
        } else {
          $("#bs-notify").notify({
            type: 'danger',
            message: { text: data.info }
          }).show();
        }
      });
      return false;
    });
  </script>

</div> <!-- /container -->

<#include "includes/overall_footer.ftl">