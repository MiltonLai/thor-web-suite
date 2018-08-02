<#include "includes/overall_header.ftl">
<#include "includes/overall_nav.ftl">

<div class="container">
  <div class="panel panel-default">
    <table class="table table-condensed">
      <tbody>
      <tr>
        <td>用户名</td><td>${se_user.name}</td>
        <td>Email</td><td>${se_user.email}</td>
        <td>手机号</td><td>${se_user.cellphone}</td>
      </tr>
      <tr>
        <td>用户组</td><td><#if se_data.role??>${se_data.role.dto.name}<#else>无</#if></td>
        <td>真实姓名</td><td>${se_user.realName}</td>
        <td>创建时间</td><td>${se_user.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>
      </tr>
      </tbody>
    </table>
  </div>
</div>

<#include "includes/overall_footer.ftl">