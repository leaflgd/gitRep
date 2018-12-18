<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
     <script type="text/javascript" src="../../../js/plug/jquery.min.js"></script> 
     <script src="../../../js/plug/html2canvas.js"></script>
</head>
<body>
    <div id="content" style="width:400px;height:500px;border:1px solid blue;">
        <img src="../../../images/400/hum_6.jpg">
        <br>
        <span><h2>Are you hear me?</h2></span>
    </div>
    <button id="btnSave">save<tton>
<script>
$(function(){   
    $('#btnSave').click(function(event) {
        html2canvas($('#content'),{
            onrendered: function(canvas) {
                //document.body.appendChild(canvas);
                convertCanvasToImage(canvas);
            }
        })

    });

    function convertCanvasToImage(canvas) {
        var image = new Image();
        image.src = canvas.toDataURL("image/png");
        document.body.appendChild(image);
        return image;
    }

});
</script>
</body>
<ml>