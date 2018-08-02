<#include "includes/overall_header.ftl">

<section class="pb60">
  <article>
    <!-- banner start -->
    <div id="banner" class="banner">
      <div class="hd">
        <ul></ul>
      </div>
      <div class="bd">
        <ul>

        </ul>
      </div>
      <div class="clear"></div>
    </div>
    <!-- banner end -->
  </article>

</section>

<div class="modal-body">
  <div class="row">
    <div class="col-md-12">
    ${se_user.name} : ${se_user.cellphone} : ${se_user.realName} : ${se_user.createdAt?string("yyyy-MM-dd HH:mm:ss")}
    </div>
  </div>
</div>

<#include "includes/overall_footer_nav.ftl">
<#include "includes/overall_footer.ftl">