<#include "includes/overall_header.ftl">

<div class="white-panel">

  <form id="loginForm" autocomplete="off">
    <!-- form start -->
    <div class="cells_container input_radius p10">
      <div class="weui_cells weui_cells_form">

        <div class="weui_cell telphone_cell">
          <div class="weui_cell_primary">
            <input type="tel" name="phone" id="phone" placeholder="请输入手机号码" maxlength="11"
                   class="weui_input" value="<#if se_data.phone?has_content>${se_data.phone}</#if>" required/><span></span>
          </div>
        </div>

        <div class="weui_cell lock_cell">
          <div class="weui_cell_primary">
            <input type="password" id="pwd" name="pwd" placeholder="请输入密码" class="weui_input" value="" /><span></span>
          </div>
        </div>

      </div>
    </div>
    <!-- form end -->
  </form>

  <div class="p15 pb0 a-grey-blue">
    <input type="submit" id="submit" class="weui_btn weui_btn_primary weui_radius" value="确认登录"/>
    <p class="pt10 mb10">

    </p>
  </div>

  <div class="ph10"></div>

</div>

<script>
  $(function () {
    // 提交登录
    $("#submit").click(function () {
      if (!validatePhone()) {
        return false;
      }

      $.ajax({
        type: 'POST',
        url: '${sys_config.root_path}/login.html',
        cache: false,
        data: $('#loginForm').serialize(),
        dataType: "json",
        success: function (data) {
          if (data.success) {
            toast(data.success);
            setTimeout(function () {
              window.location = data.field;
            }, 500);
          } else {
            toast(data.info, true);
          }
        },
        error: function () {
          toast("服务器错误", true);
        }
      });
      return false;
    });

    function validatePhone() {
      if ($("#phone").val() == "") {
        toast("请输入您的手机号码", true);
        return false;
      } else if (!checkphone($("#phone").val())) {
        toast("您的手机号码格式不正确", true);
        return false;
      }
      return true;
    }
  });

</script>

<#include "includes/overall_footer.ftl">