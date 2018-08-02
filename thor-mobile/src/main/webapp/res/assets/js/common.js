//每个页面通用js
function toast(txt, error) {
  var $toastNode = $("#toast");
  if ($toastNode.length == 0) {
    var $node = $('<div id="toast"> <div class="weui_mask_transparent"></div> <div class="weui_toast"> <i class="weui_icon_toast"></i> <p class="weui_toast_content">完成</p> </div> </div>');
    $('body').append($node);
    $toastNode = $node;
  }
  if (error != null) {
    $toastNode.find('i').attr("class", "weui_icon_toast_error");
  }
  else {
    $toastNode.find('i').attr("class", "weui_icon_toast");
  }
  $toastNode.find('.weui_toast_content').html(txt);
  $toastNode.show();

  setTimeout(function () {
    $toastNode.hide();
  }, 1000);
}

function checkemail(str) {
  return str.match(/^(?:[a-z0-9]+[_\-+.]?)*[a-z0-9]+@(?:([a-z0-9]+-?)*[a-z0-9]+.)+([a-z]{2,})+$/i);
}
function checknickname(str) {
  return str.match(/^[a-zA-Z0-9\u4e00-\u9fa5]+$/);
}
function checkrealname(str) {
  return str.match(/^([\u4e00-\u9fa5])+$/);
}
function checkphone(str) {
  return str.match(/^1[34578]\d{9}$/);
}
function checkpwd(str) {
  return str.match(/^.{6,18}$/);
}
function checkinterger(str) {
  return str.match(/00$/g);
}
function checknum(str) {
  return str.match(/^[0-9]*$/);
}
function checkcash(str) {
  return str.match(/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/);
}
function checkdate(str) {
  return str.match(/^(19|20)\d\d+(0[1-9]|1[012])+(0[1-9]|[12][0-9]|3[01])$/);
}
function checkidcard(str) {
  if (!str.match(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/)) {
    console.log("身份证号码格式不对！");
    return false;
  }
  if (str.length == 18) {
    //check date
    var date = str.substring(6, 14);
    if (!date.match(/^(19|20)\d\d+(0[1-9]|1[012])+(0[1-9]|[12][0-9]|3[01])$/)) {
      console.log("日期信息不正确:" + date);
      return false;
    }
    var factors = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];
    var veryByte = 0;
    for (i = 0; i < 17; i++) {
      veryByte = veryByte + str.charAt(i) * factors[i];
    }
    veryByte = 12 - veryByte % 11;

    switch (veryByte) {
      case 10:
        veryByte = 'X';
        break;
      case 11:
        veryByte = 0;
        break;
      case 12:
        veryByte = 1;
        break;
    }
    if (str.charAt(17).toUpperCase() != veryByte) {
      console.log("校验位错误. 应当为" + veryByte);
      return false;
    }
  }
  else {
    date = str.substring(6, 12);
    if (!date.match(/^\d{2}(0[1-9]|1[012])+(0[1-9]|[12][0-9]|3[01])$/)) {
      console.log("日期信息不正确:" + date);
      return false;
    }
  }
  return true;
}
