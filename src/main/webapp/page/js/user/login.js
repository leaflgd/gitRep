$(function(){
	//注册
	$("#no").click(function(){
	 	window.location.href="/chromosome/page/jsp/user/register.jsp";
 	});

	var btn = document.getElementById('login_btn');
	btn.onmousedown = function(){
		btn.style.background = "linear-gradient(#62d7fb, #20b4ef,#00a9ea)"
	}
	btn.onmouseup = function(){
	btn.style.background = "linear-gradient(#2ec0fb, #0e9aee,#0091ea)"
	}
	var info = document.getElementById('info');
	var user = document.getElementById('username');
	var pas = document.getElementById('password');
	/*判断用户名格式是否正确*/
	user.onblur = function(){
		var username = document.getElementById('username').value;
		var rel = /^[0-9a-zA-Z_]{3,9}$/;
		var correct = rel.test(username);
		if(!correct){
			info.style.display = 'block';
			info.innerHTML = '请输入数字、英文字符或下划线';
		}else{
			info.style.display = 'none';
			info.innerHTML = '';
		}
	}
	pas.onblur = function(){
		var password = document.getElementById('password').value;
		var rel = /^[0-9a-zA-Z]{6,16}$/;
		var correct = rel.test(password);
		if(!correct){
			info.style.display = 'block';
			info.innerHTML = '您输入的密码格式不正确';
		}else{
			info.style.display = 'none';
			info.innerHTML = '';
		}
	}
});


