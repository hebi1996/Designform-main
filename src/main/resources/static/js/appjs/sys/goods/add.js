var prefix = "/sys/user"
$(function () {
});
/**
 * 基本信息提交
 */
$("#base_save").click(function () {
    if($("#basicInfoForm").valid()){
            $.ajax({
                cache : true,
                type : "POST",
                url :"/sys/goods/save",
                data : $('#basicInfoForm').serialize(),
                async : false,
                error : function(request) {
                    laryer.alert("Connection error");
                },
                success : function(data) {
                    if (data.code == 0) {
                        parent.layer.msg("添加商品信息成功，请上传商品照片");
                        $("#goodsId").val(data.id)
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            });
        }

});