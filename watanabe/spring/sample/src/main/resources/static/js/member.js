function update() {
  var result = window.confirm("更新しますか？");
  if (result) {
    // 更新処理
    document.getElementById("upd_btn").click();
  }
}

function f_delete(){
  var result = window.confirm("削除しますか？");
  if (result) {
    // 削除処理
    document.getElementById("del_btn").click();
  }
}
