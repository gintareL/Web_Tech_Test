var A = {
	e: {
		'name': 'Please enter your name',
		'email': 'Please enter your email address',
		'i_email': 'Invalid email address',
		't_email': 'This email address is already in our database.<br />Please use the <a href="#" onclick="return A.Rec();">password recovery</a> function.',
		'login': 'Please enter the desired login',
		'i_login': 'Invalid login',
		'p_login': 'This login is not available',
		'password': 'Please enter the desired password',
		'i_password': 'The password must be at least 4 characters long',
		'password2': 'Please re-enter the password',
		'pass_nm': 'The passwords do not match',
		
		'login_e': 'Please enter your login',
		'password_e': 'Please enter the password',
		'login_f': 'Login failed',
		
		'login_e2': 'Please enter the login',
		
		'email_nf': 'Email address not found in database',
		
		'option_id': 'Please choose an option',
		'comments': 'Additional comments are required for the selected option',
		
		'email_sub': 'You are already subscribed',
		'email_ns': 'You are not subscribed',
		'email_vs': 'A validation email has been sent recently.<br />Please use the link in the email to complete your subscription<br />or try again in one hour to send another validation email.',
		
		'option_id': 'Please choose an option',
		'comments': 'Additional comments are required for the selected option',
		
		'name': 'Enter a name',
		'folder_id': 'Choose a folder',
		'p_fav': 'This item is already in this folder',
		'f_name': 'Enter a folder name',
		'p_f_name': 'You already have a folder with this name',
		
		'comment': 'Enter a comment'
	},
	
	m: {
		'reg_ok': 'Registration Successful',
		'rec_ok': 'Your password has been sent',
		'rep_ok': 'Your report has been saved and will be processed.<br />Thank you.',
		'sub_ok': 'You have been subscribed',
		'val_ok': 'A validation email has been sent.<br />Please complete your subscription<br />by following the link in the email.',
		'uns_ok': 'You have been unsubscribed',
		'rep_ok': 'Your report has been saved and will be processed.<br />Thank you.',
		'fav_ok': 'Added to your favorites',
		'comm_ok': 'Your comment has been saved and is pending moderation.'
	},
	
	Reg: function() {
		Ui.Req('/req/account/register.req.php');
		return false;
	},
	Log: function(ec) {
		var ec = ec || 0;
		Ui.Req('/req/account/login.req.php', {
			method: 'post',
			postBody: 'ec='+ec+'&bt='+encodeURIComponent(document.location.href),
			onAfterDraw: function() {
				document.xdfrm.login.focus(); 
			}
		});
		return false;
	},
	Rec: function() {
		Ui.Req('/req/account/recover.req.php', {
			onAfterDraw: function() {
				document.xdfrm.email.focus(); 
			}
		});
		return false;
	},
	Nl: function() {
		Ui.Req('/req/nl/nl.req.php', {
			onAfterDraw: function() {
				document.xdfrm.email.focus(); 
			}
		});
		return false;
	},
	
	onLogin: function() {
		
	},
	
	onLogout: function() {
		
	},
	
	LoadOps: function() {
		new Ajax.Request('/req/account/ops.req.php', {
			onComplete: function(t, j, o) {
				$('usrops').innerHTML = t.responseText;
			}
		});
	},
	
	Logout: function() {
		new Ajax.Request('/req/account/logout.req.php', {
			onComplete: function(t, j, o) {
				A.onLogout();
				$('usrops').innerHTML = t.responseText;
			}
		})
		return false;
	},
	
	LogDo: function(f) {
		if (!this.LogVal(f))
			return false;
		f.btn1.disabled = true;
		new Ajax.Request(
			'/req/account/login_do.req.php', {
				method: 'post',
				postBody: A.GFD(f),
				f: f,
				onComplete: function(t, j, o) {
					o.options.f.btn1.disabled = false;
					try {
						a = eval(t.responseText);
						if (a && a.length) {
							var a = a[0];
							if (!a.ea.length) {
								Ui.Cl();
								A.onLogin();
								A.LoadOps();
							} else {
								for (var i = 0; i < a.ea.length; i++) {
									A.E(a.ea[i][0], A.e[a.ea[i][1]], 'xe');
								}
							}
						}
					} catch (e) {
						alert(t.responseText);
					}
				}
			}
		);
		return false;
	},
	RegDo: function(f) {
		if (!this.RegVal(f))
			return false;
		
		f.btn1.disabled = true;
		new Ajax.Request('/req/account/register_do.req.php', {
			method: 'post',
			postBody: A.GFD(f),
			f: f,
			onComplete: function(t, j, o) {
				o.options.f.btn1.disabled = false;
				try {
					a = eval(t.responseText);
					if (a && a.length) {
						var a = a[0];
						if (!a.ea.length) {
							Ui.Cl();
							A.onLogin();
							A.LoadOps();
						} else {
							for (var i = 0; i < a.ea.length; i++) {
								A.E(a.ea[i][0], A.e[a.ea[i][1]], 'xe');
							}
						}
					}
				} catch (e) {
					alert(t.responseText);
				}
			}
		});
		return false;
	},
	
	RecDo: function(f) {
		if (!this.RecVal(f))
			return false;
		$('xloading').style.display = 'block';
		f.btn1.disabled = true;
		A.M('main', '', 'xm');
		new Ajax.Request('/req/account/recover_do.req.php', {
			method: 'post',
			postBody: A.GFD(f),
			f: f,
			onComplete: function(t, j, o) {
				o.options.f.btn1.disabled = false;
				$('xloading').style.display = 'none';
				try {
					a = eval(t.responseText);
					if (a && a.length) {
						var a = a[0];
						if (!a.ea.length) {
							o.options.f.email.value = '';
							A.M('main', A.m['rec_ok'], 'xm');
						} else {
							for (var i = 0; i < a.ea.length; i++) {
								A.E(a.ea[i][0], A.e[a.ea[i][1]], 'xe');
							}
						}
					}
				} catch (e) {
					alert(t.responseText);
				}
			}
		});
		return false;
	},
	
	NlDo: function(f) {
		if (!this.NlVal(f))
			return false;
		$('xloading').style.display = 'block';
		f.btn1.disabled = true;
		A.M('main', '', 'xm');
		new Ajax.Request('/req/nl/nl_do.req.php', {
			method: 'post',
			postBody: A.GFD(f),
			f: f,
			onComplete: function(t, j, o) {
				o.options.f.btn1.disabled = false;
				$('xloading').style.display = 'none';
				try {
					a = eval(t.responseText);
					if (a && a.length) {
						var a = a[0];
						if (!a.ea.length) {
							A.M('main', A.m[a.ma], 'xm');
						} else {
							for (var i = 0; i < a.ea.length; i++) {
								A.E(a.ea[i][0], A.e[a.ea[i][1]], 'xe');
							}
						}
					}
				} catch (e) {
					alert(t.responseText);
				}
			}
		});
		return false;
	},
	
	NlDo2: function(f) {
		if (!this.NlVal2(f))
			return false;
		$('hload').style.display = 'block';
		f.btn1.disabled = true;
		A.M('main', '');
		new Ajax.Request('/req/nl/nl_do.req.php', {
			method: 'post',
			postBody: A.GFD(f),
			f: f,
			onComplete: function(t, j, o) {
				o.options.f.btn1.disabled = false;
				$('hload').style.display = 'none';
				try {
					a = eval(t.responseText);
					if (a && a.length) {
						var a = a[0];
						if (!a.ea.length) {
							A.M('main', A.m[a.ma]);
						} else {
							for (var i = 0; i < a.ea.length; i++) {
								A.E(a.ea[i][0], A.e[a.ea[i][1]]);
							}
						}
					}
				} catch (e) {
					alert(t.responseText);
				}
			}
		});
		return false;
	},
	
	LogVal: function(f) {
		this.p = true;
		this.CE(f);
		if (!f.login.value.length)
			this.p = this.E(f.login, this.e['login_e'], 'xe');
		else if (!val_login(f.login.value))
			this.p = this.E(f.login, this.e['i_login'], 'xe');
		if (!f.password.value.length)
			this.p = this.E(f.password, this.e['password_e'], 'xe');
		else if (f.password.value.length < 4)
			this.p = this.E(f.password, this.e['i_password'], 'xe');
		return this.p;
	},
	RegVal: function(f) {
		this.p = true;
		this.CE(f);
		if (!f.name.value.length)
			this.p = this.E(f.name, this.e['name'], 'xe');
		if (!f.email.value.length)
			this.p = this.E(f.email, this.e['email'], 'xe');
		else if (!val_email(f.email.value))
			this.p = this.E(f.email, this.e['i_email'], 'xe');
		if (!f.login.value.length)
			this.p = this.E(f.login, this.e['login'], 'xe');
		else if (!val_login(f.login.value))
			this.p = this.E(f.login, this.e['i_login'], 'xe');
		if (!f.password.value.length)
			this.p = this.E(f.password, this.e['password'], 'xe');
		else if (f.password.value.length < 4)
			this.p = this.E(f.password, this.e['i_password'], 'xe');
		if (!f.password2.value.length)
			this.p = this.E(f.password2, this.e['password2'], 'xe');
		else if (f.password.value != f.password2.value)
			this.p = this.E(f.password2, this.e['pass_nm'], 'xe');
		return this.p;
	},
	RecVal: function(f) {
		this.p = true;
		this.CE(f);
		
		if (!f.email.value.length)
			this.p = this.E(f.email, this.e['email'], 'xe');
		else if (!val_email(f.email.value))
			this.p = this.E(f.email, this.e['i_email'], 'xe');
		
		return this.p;
	},
	NlVal: function(f) {
		this.p = true;
		this.CE(f);
		if (!f.email.value.length)
			this.p = this.E(f.email, this.e['email'], 'xe');
		else if (!val_email(f.email.value))
			this.p = this.E(f.email, this.e['i_email'], 'xe');
		return this.p;
	},
	NlVal2: function(f) {
		this.p = true;
		this.CE(f);
		if (!f.email.value.length)
			this.p = this.E(f.email, this.e['email']);
		else if (!val_email(f.email.value))
			this.p = this.E(f.email, this.e['i_email']);
		return this.p;
	},
	
	p: false,
	E: function(e, t, prefix) {
		var prefix = prefix || 'e';
		if (a = $(prefix + '_'+(typeof(e) == 'string' ? e : e.name))) {
			a.style.display = t ? 'block' : 'none';
			a.innerHTML = t;
		}
		else
			alert(t);
		if (this.p) {
			try {
				e.focus();
			} catch (e) { };
		}
		return false;
	},
	M: function(e, t, prefix) {
		var prefix = prefix || 'm';
		var a = $(prefix+'_'+e);
		if (!a)
		{
			alert(t);
			return false;
		}
		a.style.display = t ? 'block' : 'none';
		a.innerHTML = t;
		
		return false;
	},
	GFE: function(f) {
		var ret = [];
		var e = f.getElementsByTagName('input');
		for (var i = 0; i < e.length; i++)
			if (e[i].type.toLowerCase() != 'submit')
				ret.push(e[i]);
		var e = f.getElementsByTagName('textarea');
		for (var i = 0; i < e.length; i++)
			ret.push(e[i]);
		var e = f.getElementsByTagName('select');
		for (var i = 0; i < e.length; i++)
			ret.push(e[i]);
		return ret;
	},
	GFD: function(f) {
		var s = '';
		var e = this.GFE(f);
		for (var i = 0; i < e.length; i++)
			if ((e[i].tagName.toLowerCase() == 'input' && (e[i].type == 'text' || e[i].type == 'password' || e[i].type == 'hidden')) || e[i].tagName.toLowerCase() == 'textarea' || (e[i].tagName.toLowerCase() == 'select' && !e[i].multiple))
				s += '&' + encodeURIComponent(e[i].name) + '=' + encodeURIComponent(e[i].value);
			else if (e[i].tagName.toLowerCase() == 'select' && e[i].multiple) {
				for (var j = 0; j < e[i].options.length; j++)
					if (e[i].options[j].selected)
						s += '&' + encodeURIComponent(e[i].name) + '=' + encodeURIComponent(e[i].options[j].value);
			}
			else if (e[i].tagName.toLowerCase() == 'input' && e[i].type == 'checkbox')
				s += '&' + encodeURI(e[i].name).replace(/&/g, '%26') + '=' + (e[i].checked?1:0);
			else if (e[i].tagName.toLowerCase() == 'input' && e[i].type == 'radio' && e[i].checked)
				s += '&' + encodeURI(e[i].name).replace(/&/g, '%26') + '=' + encodeURIComponent(e[i].value);
		return s.substr(1);
	},
	GFA: function(f) {
		var a = {};
		var e = this.GFE(f);
		for (var i = 0; i < e.length; i++)
			if ((e[i].tagName.toLowerCase() == 'input' && (e[i].type == 'text' || e[i].type == 'password' || e[i].type == 'hidden')) || e[i].tagName.toLowerCase() == 'textarea' || (e[i].tagName.toLowerCase() == 'select' && !e[i].multiple))
				a[e[i].name] = e[i].value;
			else if (e[i].tagName.toLowerCase() == 'select' && e[i].multiple) {
				for (var j = 0; j < e[i].options.length; j++)
					if (e[i].options[j].selected)
						a[e[i].name] = e[i].options[j].value;
			}
			else if (e[i].tagName.toLowerCase() == 'input' && e[i].type == 'checkbox')
				a[e[i].name] = e[i].checked?1:0;
			else if (e[i].tagName.toLowerCase() == 'input' && e[i].type == 'radio' && e[i].checked)
				a[e[i].name] = e[i].value;
		return a;
	},
	A2Url: function(a) {
		var s = '';
		for (var k in a)
			s += '&' + encodeURIComponent(k) + '=' + encodeURIComponent(a[k]);
		return s.substr(1);
	},
	CE: function(f) {
		var a = f.getElementsByTagName('div');
		for (var i = 0; i < a.length; i++)
			if (a[i].className == 'err' || a[i].className == 'errc') {
				a[i].style.display = 'none';
				a[i].innerHTML = '';
			}
	},
	CF: function(f) {
		var b = this.GFE(f);
		for (var i = 0; i < b.length; i++)
			if (b[i].type.toUpperCase() != 'RADIO' && b[i].type.toUpperCase() != 'BUTTON')
				b[i].value = '';
	},
	Lo: function(f) {
		$('loading').style.display = f ? 'block' : 'none';
	},
	RLCl: function(id) {
		$('reql'+id).innerHTML = '';
		$('reql0').style.height = '';
		return false;
	}
}

var I = {
	C: function(t, i) {
		new Ajax.Request(
			'/req/image/click.req.php', {
				method: 'post',
				asynchronous: false,
				postBody: 't='+t+'&id='+i
			}
		);
	},
	Emb: function(id) {
		Ui.Req('/req/image/embed.req.php', {
			method: 'post',
			postBody: 'id='+id
		});
		return false;
	},
	Rep: function(id) {
		Ui.Req('/req/image/report.req.php', {
			method: 'post',
			postBody: 'id='+id
		});
		return false;
	},
	RepDo: function(f, id) {
		if (!this.RepVal(f))
			return false;
		
		f.btn1.disabled = true;
		A.M('main', '', 'xm');
		new Ajax.Request(
			'/req/image/report_do.req.php', {
				method: 'post',
				postBody: 'id='+id+'&'+A.GFD(f),
				f: f,
				onComplete: function(t, j, o) {
					o.options.f.btn1.disabled = false;
					try {
						a = eval(t.responseText);
						if (a && a.length) {
							var a = a[0];
							if (!a.ea.length) {
								A.M('main', A.m['rep_ok'], 'xm');
								A.CF(o.options.f);
							} else {
								for (var i = 0; i < a.ea.length; i++) {
									A.E(a.ea[i][0], A.e[a.ea[i][1]], 'xe');
								}
							}
						}
					} catch (e) {
						alert(t.responseText);
					}
				}
			}
		);
		
		return false;
	},
	RepVal: function(f) {
		A.p = true;
		A.CE(f);
		
		var oid = get_option(f.option_id);
		if (oid === false)
			A.p = A.E(x = 'option_id', A.e[x], 'xe');
		else if (oid == 7 && !f.comments.value.length) {
			if (!f.comments.value.length)
			A.p = A.E(f.comments, A.e['comments'], 'xe');
		}
		
		return A.p;
	},
	R: function(id, rr) {
		Ui.slowLoad(1);
		new Ajax.Request('/req/image/rate.req.php', {
			method: 'post',
			postBody: 'id='+id+'&rr='+rr,
			id: id,
			rr: rr,
			onComplete: function(t, j, o) {
				Ui.slowLoad(0);
				
				if (t.responseText == 'NLI') {
					A.Log(1);
					A.tmp_id = o.options.id;
					A.tmp_rr = o.options.rr;
					A.onLogin = function() {
						I.R(A.tmp_id, A.tmp_rr);
						A.onLogin = function() { };
					}
					return;
				}
				
				try {
					a = eval(t.responseText);
					if (a && a.length) {
						var a = a[0];
						$('ra_cnt').innerHTML = a.rating;
						$('ra_btn_up').className = o.options.rr == 1 ? 'btn blue active' : 'btn blue';
						$('ra_btn_dn').className = o.options.rr == -1 ? 'btn active fl' : 'btn fl';
					}
				} catch (e) {
					alert(t.responseText + e);
				}
			}
		});
		
		return false;
	},
	F: function(id) {
		Ui.Req('/req/image/fav.req.php', {
			method: 'post',
			postBody: 'id='+id,
			id: id,
			onBeforeDraw: function(t, j, o) {
				if (t.responseText == 'NLI') {
					A.Log(1);
					A.tmp_id = o.options.id;
					A.onLogin = function() {
						I.F(A.tmp_id);
						A.onLogin = function() { };
					}
					return false;
				}
			}
		});
		return false;
	},
	FavDo: function(f, id) {
		if (!this.FavVal(f))
			return false;
		
		$('xloading').style.display = 'block';
		f.btn1.disabled = true;
		A.M('main', '', 'xm');
		new Ajax.Request(
			'/req/image/fav_do.req.php', {
				method: 'post',
				postBody: 'id='+id+'&'+A.GFD(f),
				f: f,
				onComplete: function(t, j, o) {
					$('xloading').style.display = 'none';
					o.options.f.btn1.disabled = false;
					try {
						a = eval(t.responseText);
						if (a && a.length) {
							var a = a[0];
							if (!a.ea.length) {
								A.M('main', A.m['fav_ok'], 'xm');
								$('fav_cnt').innerHTML = a.favs;
								$('fav_btn').className = 'btn active';
								for (var i = 0; i < a.folder_add.length; i++) {
									o.options.f.folder_id.options[o.options.f.folder_id.options.length] = new Option(a.folder_add[i].name, a.folder_add[i].folder_id);
								}
								var fid = parseInt(a.folder_id);
								for (var i = 0; i < o.options.f.folder_id.options.length; i++) {
									if (o.options.f.folder_id.options[i].value == fid)
										o.options.f.folder_id.options[i].disabled = true;
								}
							} else {
								for (var i = 0; i < a.ea.length; i++) {
									A.E(a.ea[i][0], A.e[a.ea[i][1]], 'xe');
								}
							}
						}
					} catch (e) {
						alert(t.responseText);
					}
				}
			}
		);
		
		return false;
	},
	FavVal: function(f) {
		A.p = true;
		A.CE(f);
		if (!f.name.value.length)
			A.p = A.E(f.name, A.e['name'], 'xe');
		if (parseInt(f.folder_id.value) == -1 && !f.f_name.value.length)
			A.p = A.E(f.f_name, A.e['f_name'], 'xe');
		return A.p;
	},
	Like: function(id, l) {
		new Ajax.Request('/req/image/like.req.php', {
			method:'post',
			postBody:'id='+id+'&l='+l,
			onComplete: function(t) {
				var a = t.responseText.split("\n");
				if (a[0] == 'OK') {
					
				} else {
					//alert(t.responseText);
				}
			}
		});
		return false;
	},
	Co: function(id) {
		Ui.Req('/req/image/comm.req.php', {
			method: 'post',
			postBody: 'id='+id,
			id: id,
			onBeforeDraw: function(t, j, o) {
				if (t.responseText == 'NLI') {
					A.Log(1);
					A.tmp_id = o.options.id;
					A.onLogin = function() {
						I.Co(A.tmp_id);
						A.onLogin = function() { };
					}
					return false;
				}
			},
			onAfterDraw: function(t, j, o) {
				document.xdfrm.comment.focus(); 
			}
		});
		return false;
	},
	CoDo: function(f, id) {
		if (!this.CoVal(f))
			return false;
			
		$('xloading').style.display = 'block';
		f.btn1.disabled = true;
		A.M('main', '', 'xm');
		new Ajax.Request(
			'/req/image/comm_do.req.php', {
				method: 'post',
				postBody: 'id='+id+'&'+A.GFD(f),
				f: f,
				onComplete: function(t, j, o) {
					$('xloading').style.display = 'none';
					o.options.f.btn1.disabled = false;
					try {
						a = eval(t.responseText);
						if (a && a.length) {
							var a = a[0];
							if (!a.ea.length) {
								A.M('main', A.m['comm_ok'], 'xm');
								A.CF(o.options.f);
							} else {
								for (var i = 0; i < a.ea.length; i++) {
									A.E(a.ea[i][0], A.e[a.ea[i][1]], 'xe');
								}
							}
						}
					} catch (e) {
						alert(t.responseText);
					}
				}
			}
		);
		
		return false;
	},
	CoVal: function(f) {
		A.p = true;
		A.CE(f);
		if (!f.comment.value.length)
			A.p = A.E(f.comment, A.e['comment'], 'xe');
		return A.p;
	}
}

var W = {
	Z: function(id) {
		var ws = getWS();
		Ui.Req('/req/wps/zoom.req.php', {
			method: 'post',
			postBody: 'id='+id+'&w='+ws[0]+'&h='+ws[1],
			onAfterDraw: function() {
				$('ajopc').style.height = getDocHeight() + 'px';
				var tb = $('ajopc').getElementsByTagName('table')[0];
				tb.style.marginTop = (getScrollTop() + 20) + 'px';
			}
		});
		return false;
	},
	C: function(id) {
		new Ajax.Request(
			'/req/wps/click.req.php', {
				method: 'post',
				asynchronous: false,
				postBody: 'id='+id
			}
		);
	},
	Emb: function(id) {
		Ui.Req('/req/wps/embed.req.php', {
			method: 'post',
			postBody: 'id='+id
		});
		return false;
	},
	Rep: function(id) {
		Ui.Req('/req/wps/report.req.php', {
			method: 'post',
			postBody: 'id='+id
		});
		return false;
	},
	RepDo: function(f, id) {
		if (!this.RepVal(f))
			return false;
		
		f.btn1.disabled = true;
		A.M('main', '', 'xm');
		new Ajax.Request(
			'/req/wps/report_do.req.php', {
				method: 'post',
				postBody: 'id='+id+'&'+A.GFD(f),
				f: f,
				onComplete: function(t, j, o) {
					o.options.f.btn1.disabled = false;
					try {
						a = eval(t.responseText);
						if (a && a.length) {
							var a = a[0];
							if (!a.ea.length) {
								A.M('main', A.m['rep_ok'], 'xm');
								A.CF(o.options.f);
							} else {
								for (var i = 0; i < a.ea.length; i++) {
									A.E(a.ea[i][0], A.e[a.ea[i][1]], 'xe');
								}
							}
						}
					} catch (e) {
						alert(t.responseText);
					}
				}
			}
		);
		
		return false;
	},
	RepVal: function(f) {
		A.p = true;
		A.CE(f);
		
		var oid = get_option(f.option_id);
		if (oid === false)
			A.p = A.E(x = 'option_id', A.e[x], 'xe');
		else if (oid == 7 && !f.comments.value.length) {
			if (!f.comments.value.length)
			A.p = A.E(f.comments, A.e['comments'], 'xe');
		}
		
		return A.p;
	},
	R: function(id, rr) {
		new Ajax.Request('/req/wps/rate.req.php', {
			method: 'post',
			postBody: 'id='+id+'&rr='+rr,
			id: id,
			rr: rr,
			onComplete: function(t, j, o) {
				if (t.responseText == 'NLI') {
					A.Log(1);
					A.tmp_id = o.options.id;
					A.tmp_rr = o.options.rr;
					A.onLogin = function() {
						W.R(A.tmp_id, A.tmp_rr);
						A.onLogin = function() { };
					}
					return;
				}
				
				try {
					a = eval(t.responseText);
					if (a && a.length) {
						var a = a[0];
						$('ra_cnt').innerHTML = a.rating;
						$('ra_btn_up').className = o.options.rr == 1 ? 'btn blue active' : 'btn blue';
						$('ra_btn_dn').className = o.options.rr == -1 ? 'btn active fl' : 'btn fl';
					}
				} catch (e) {
					alert(t.responseText + e);
				}
			}
		});
		
		return false;
	},
	F: function(id, ff) {
		new Ajax.Request('/req/wps/fav.req.php', {
			method: 'post',
			postBody: 'id='+id+'&ff='+ff,
			id: id,
			ff: ff,
			onComplete: function(t, j, o) {
				if (t.responseText == 'NLI') {
					A.Log(1);
					A.tmp_id = o.options.id;
					A.tmp_ff = o.options.ff;
					A.onLogin = function() {
						W.F(A.tmp_id, A.tmp_ff);
						A.onLogin = function() { };
					}
					return;
				}
				
				try {
					a = eval(t.responseText);
					if (a && a.length) {
						var a = a[0];
						$('fav_cnt').innerHTML = a.cnt;
						(fb = $('fav_btn')).className = o.options.ff ? 'btn active' : 'btn';
						fb.innerHTML = o.options.ff ? '<i class="icon-heart-empty"></i> Unfav' : '<i class="icon-heart"></i> Fav';
						eval("fb.onclick = function() { W.F("+o.options.id+", "+(o.options.ff ? 0 : 1)+"); return false; }");
					}
				} catch (e) {
					alert(t.responseText);
				}
			}
		});
		return false;
	}
}

var IA = {
	e: {
		'cat_id': 'Choose the category',
		'name': 'Enter the name',
		'keywords': 'Enter the keywords'
	},
	m: {
		'edit_ok': 'Saved',
		'del_ok': 'Deleted'
	},
	Edit: function(id) {
		Ui.Req('/req/image/edit.req.php', {
			method: 'post',
			postBody: 'id='+id,
			onBeforeDraw: function(t, j, o) {
				if (t.responseText == 'NLI') {
					A.Log();
					A.tmp_id = o.options.id;
					A.onLogin = function() {
						IA.Edit(A.tmp_id);
						A.onLogin = function() { };
					}
					return false;
				}
			}
		});
		return false;
	},
	EditVal: function(f) {
		A.p = true;
		A.CE(f);
		if (!f.cat_id.value.length)
			A.p = A.E(f.cat_id, IA.e['cat_id'], 'xe');
		if (!f.name.value.length)
			A.p = A.E(f.name, IA.e['name'], 'xe');
		if (!f.keywords.value.length)
			A.p = A.E(f.keywords, IA.e['keywords'], 'xe');
		return A.p;
	},
	EditDo: function(f, id) {
		if (!this.EditVal(f))
			return false;
		
		$('xloading').style.display = 'block';
		f.btn1.disabled = true;
		A.M('main', '', 'xm');
		new Ajax.Request(
			'/req/image/edit_do.req.php', {
				method: 'post',
				postBody: 'id='+id+'&'+A.GFD(f),
				f: f,
				onComplete: function(t, j, o) {
					$('xloading').style.display = 'none';
					o.options.f.btn1.disabled = false;
					try {
						a = eval(t.responseText);
						if (a && a.length) {
							var a = a[0];
							if (!a.ea.length) {
								A.M('main', IA.m['edit_ok'], 'xm');
								
							} else {
								for (var i = 0; i < a.ea.length; i++) {
									A.E(a.ea[i][0], IA.e[a.ea[i][1]], 'xe');
								}
							}
						}
					} catch (e) {
						alert(t.responseText);
					}
				}
			}
		);
		
		return false;
	},
	Del: function(id) {
		Ui.Req('/req/image/del.req.php', {
			method: 'post',
			postBody: 'id='+id,
			onBeforeDraw: function(t, j, o) {
				if (t.responseText == 'NLI') {
					A.Log();
					A.tmp_id = o.options.id;
					A.onLogin = function() {
						IA.Del(A.tmp_id);
						A.onLogin = function() { };
					}
					return false;
				}
			}
		});
		return false;
	},
	DelDo: function(f, id) {
		$('xloading').style.display = 'block';
		f.btn1.disabled = true;
		f.btn2.disabled = true;
		A.M('main', '', 'xm');
		new Ajax.Request(
			'/req/image/del_do.req.php', {
				method: 'post',
				postBody: 'id='+id+'&'+A.GFD(f),
				f: f,
				onComplete: function(t, j, o) {
					$('xloading').style.display = 'none';
					o.options.f.btn1.disabled = false;
					o.options.f.btn2.disabled = false;
					try {
						a = eval(t.responseText);
						if (a && a.length) {
							var a = a[0];
							if (!a.ea.length) {
								A.M('main', IA.m['del_ok'], 'xm');
								
							} else {
								for (var i = 0; i < a.ea.length; i++) {
									A.E(a.ea[i][0], IA.e[a.ea[i][1]], 'xe');
								}
							}
						}
					} catch (e) {
						alert(t.responseText);
					}
				}
			}
		);
		
		return false;
	},
	Gdc: function(id, c) {
		var b = $('gdc_'+id);
		b.className = c ? 'btn med fl blue active' : 'btn med fl red';
		b.innerHTML = c ? '<i class="icon-google-plus"></i>&nbsp;&nbsp;GAD ON' : '<i class="icon-google-plus"></i>&nbsp;&nbsp;GAD off';
		var cf = c ? 1 : 0;
		$('hload').style.display = 'block';
		new Ajax.Request('/req/image/gdc.req.php', {
			method: 'post',
			postBody: 'id='+id+'&c='+cf,
			onComplete: function(t, j, o) {
				$('hload').style.display = 'none';
				var a = t.responseText.split("\n");
				if (a[0] == 'OK') {
					
				} else {
					//alert(t.responseText);
				}
			}
		});
		return false;
	},
	SD: function(id) {
		alert(id);
		return false; 
	}
}

var WA = {
	Gdc: function(id, c) {
		var b = $('gdc_'+id);
		b.className = c ? 'btn med fl blue active' : 'btn med fl red';
		b.innerHTML = c ? '<i class="icon-google-plus"></i>&nbsp;&nbsp;GAD ON' : '<i class="icon-google-plus"></i>&nbsp;&nbsp;GAD off';
		var cf = c ? 1 : 0;
		$('hload').style.display = 'block';
		new Ajax.Request('/req/wps/gdc.req.php', {
			method: 'post',
			postBody: 'id='+id+'&c='+cf,
			onComplete: function(t, j, o) {
				$('hload').style.display = 'none';
				var a = t.responseText.split("\n");
				if (a[0] == 'OK') {
					
				} else {
					//alert(t.responseText);
				}
			}
		});
		return false;
	}
}

var box = {
	shortest: function(colh) {
		var a = 0;
		for (var i = 1; i < colh.length; i++)
			if (colh[i] < colh[a])
				a = i;
		return a;
	},
	
	longest: function(colh) {
		var a = 0;
		for (var i = 1; i < colh.length; i++)
			if (colh[i] > colh[a])
				a = i;
		return a;
	},
	
	Arrange: function(ce) {
		var i = 0;
		var colh = [0, 0, 0, 0];
		var rmar = 32;
		var bmar = 20;
		var lmar = 30;
		var tmar = 20;
		var a = ce.getElementsByTagName('li');
		for (var i = 0; i < a.length; i++) {
			var sc = this.shortest(colh);
			var cw = a[i].clientWidth;
			var ch = a[i].clientHeight;
			
			var xp = sc * (cw + rmar);
			var yp = colh[sc];
			
			a[i].style.position = 'absolute';
			a[i].style.left = xp + 'px';
			a[i].style.top = yp + 'px';
			
			colh[sc] += ch + bmar;
		}
		
		var lc = this.longest(colh);
		var ch = colh[lc];
		if (a.length)
			ce.style.height = ch + 'px';
	}
}

var PG = {
	N: false, P:false, cp:1, pt:1, rp:false, ltid:false, bl:false,
	I: function() {
		document.onkeydown = function(e) {
			var e = e || window.event;
			if (e.keyCode == 39 && PG.N) {
				if (PG.CK(e))
					return true;
				typeof(PG.N) == 'function' ? PG.N() : document.location.href = PG.N;
				return false;
			}
			if (e.keyCode == 37 && PG.P) {
				if (PG.CK(e))
					return true;
				typeof(PG.P) == 'function' ? PG.P() : document.location.href = PG.P;
				return false;
			}
			return true;
		}
		
		var x = document.location.hash.substr(1);
		if (x && !isNaN(parseInt(x)) && x != this.cp)
			PG.R(parseInt(x));
			
		if (this.rp) {
			this.P = function() {
				if (this.cp > 1)
					this.R(this.cp - 1);
			}
			this.N = function() {
				if (this.cp < this.pt)
					this.R(this.cp + 1);
			}
		}
	},
	CK: function(e) {
		if (e.target) {
			if (e.target.tagName.toUpperCase() != 'HTML' && e.target.tagName.toUpperCase() != 'BODY')
				return true;
			return false;
		}
		else if (e.srcElement) {
			if (e.srcElement != document.body)
				return true;
			return false;
		}
		return false;
	},
	R: function(p) {
		if (!this.rp)
			return true;
		
		this.ltid = setTimeout("$('hload').style.display = 'block';", 100);
		
		new Ajax.Request(this.rp, {
			method:'post',
			postBody:'p='+p,
			p:p,
			onComplete: function(t, j, o) {
				PG.cp = o.options.p;
				if (PG.bl)
					PG.bl(t, j, o);
				$('jspg').innerHTML = t.responseText;
				if (o.options.p > 1)
					document.location.href = '#'+o.options.p;
				else
					document.location.href = '#1';
				
				if (document.documentElement && document.documentElement.scrollTop)
					document.documentElement.scrollTop = 0;
				else if (document.body && document.body.scrollTop)
					document.body.scrollTop = 0;
				
				if (PG.ltid) {
					clearTimeout(PG.ltid);
					PG.ltid = false;
				}
				
				$('hload').style.display = 'none';
			}
		})
		
		return false;
	}
}

var S = {
	C: function(sid, lid) {
		new Ajax.Request('/req/search/click.req.php', {
			method: 'post',
			postBody: 'search_id='+sid+'&link_id='+lid,
			asynchronous:false
		})
	},
	LS: function(id) {
		new Ajax.Request('/req/search/similar.req.php', {
			method:'post',
			postBody:'id='+id,
			onComplete: function(t) {
				$('related').innerHTML = t.responseText;
			}
		})
		return false;
	},
	PV: function(id) {
		var def = '<div style="padding:40px 0 0 0;"><div class="loading" style="display:block;margin:auto;"></div></div>';
		var spe = $('spv_'+id);
		if (spe.innerHTML)
			return;
		spe.innerHTML = def;
		new Ajax.Request('/req/search/preview.req.php', {
			method:'post',
			postBody:'id='+id,
			id:id,
			onComplete: function(t, j, o) {
				var spe = $('spv_'+o.options.id);
				spe.innerHTML = t.responseText;
				if (!t.responseText.length) {
					//spe.style.width = 'auto';
					spe.style.height = 'auto';
					spe.innerHTML = '<div class="errc" style="margin:0;">No preview</div>'
				}
			}
		});
	},
	tid: 0,
	tout: 200,
	q: '',
	Q: function(q) {
		this.q = q;
		if (!q.length) {
			$('sres').innerHTML = '';
			$('sres').style.display = 'none';
		} else {
			if (this.tid)
				clearTimeout(this.tid);
			this.tid = setTimeout('S.DS();', this.tout);
		}
	},
	DS: function() {
		new Ajax.Request('/req/search/sug.req.php', {
			method:'post',
			postBody:'q='+encodeURIComponent(this.q),
			onComplete: function(t) {
				if (t.responseText.length) {
					$('sres').innerHTML = t.responseText;
					$('sres').style.display = 'block';
				} else {
					$('sres').style.display = 'none';
				}
			}
		});
	},
	GDC: function(s) {
		if (s.indexOf('gad_nok') != -1 && $('lbin')) {
			$('lbin').style.display = 'none';
		} else if ($('lbin')) {
			$('lbin').style.display = 'block';
		}
	}
}

var Ui = {
	ltid: false,
	Req: function(f, p) {
		var p = p || {};
		p.onComplete = function(t, j, o) {
			if (o.options.onBeforeDraw) {
				var ret = o.options.onBeforeDraw(t, j, o);
				if (ret === false)
					return false;
			}
			
			$('ajopr').innerHTML = t.responseText;
			$('ajopc').style.height = (getScrollTop() + getDocHeight() + 100) + 'px';
			var tb = $('ajopc').getElementsByTagName('table')[0];
			tb.style.marginTop = (getScrollTop() + 60) + 'px';
			
			if (o.options.onAfterDraw)
				o.options.onAfterDraw(t, j, o);
		};
		
		new Ajax.Request(f, p);
	},
	Cl: function() {
		$('ajopr').innerHTML = '';
		return false;
	},
	slowLoad: function(c) {
		if (c) {
			this.ltid = setTimeout("$('hload').style.display = 'block';", 100);
		} else {
			if (this.ltid) {
				clearTimeout(this.ltid);
				this.ltid = false;
			}
			$('hload').style.display = 'none';
		}
	}
}

var Box = {
	shortest: function(colh) {
		var a = 0;
		for (var i = 1; i < colh.length; i++)
			if (colh[i] < colh[a])
				a = i;
		return a;
	},
	
	longest: function(colh) {
		var a = 0;
		for (var i = 1; i < colh.length; i++)
			if (colh[i] > colh[a])
				a = i;
		return a;
	},
	
	Arr: function(el, dw) {
		var ws = getWS();
		var dw = dw || (ws[0] - 20);
		var a = el.getElementsByTagName('li');
		if (!a.length)
			return;
		var ew = a[0].clientWidth;
		var margin = 5;
		var cmargin = 10;
		var fit = Math.floor((dw - cmargin * 2) / (ew + margin * 2));
		fit = Math.min(fit, a.length);
		
		var margin = Math.floor((dw - cmargin * 2 - ew * fit) / 2  / fit);
		var hmargin = Math.floor(margin * 1);
		
		if (a.length <= fit)
			hmargin = 5;
		
		var elw = fit * ew + fit * margin + margin;
		el.style.position = 'relative';
		el.style.width = elw + 'px';
		el.style.margin = '-'+hmargin+'px auto';
		
		var colh = [];
		for (var j = 0; j < fit; j++)
			colh.push(0);
		
		for (var i = 0; i < a.length; i++) {
			a[i].style.margin = hmargin + 'px ' + margin + 'px';
			
			var sc = this.shortest(colh);
			var ch = a[i].clientHeight;
			
			var xp = sc * (ew + margin);
			var yp = hmargin + colh[sc];
			
			a[i].style.position = 'absolute';
			a[i].style.left = xp + 'px';
			a[i].style.top = yp + 'px';
			
			colh[sc] += ch + hmargin;
		}
		
		var lc = this.longest(colh);
		var ch = colh[lc] + 2 * hmargin;
		if (a.length > 2) {
			el.style.height = ch + 'px';
			var navw = fit * ew + fit * margin - margin;
			$('nav1').style.width = navw + 'px';
			if (sne = $('csubnav'))
				sne.style.width = navw + 'px';
		}
	}
}

var T = {
	ck: [],
	A: function(id, k) {
		if (!this.ck[id])
			this.ck[id] = 0;
		
		$('th_'+id+'_'+this.ck[id]).className = '';
		$('tc_'+id+'_'+this.ck[id]).style.display = 'none';
		
		$('th_'+id+'_'+k).className = 'active';
		$('tc_'+id+'_'+k).style.display = 'block';
		
		this.ck[id] = k;
		return false;
	}
}

function getScrollTop() { 
	if (typeof(pageYOffset) != 'undefined')
        return pageYOffset; 
	var B = document.body;
	var D = document.documentElement; 
	D = (D.clientHeight) ? D: B;
	return D.scrollTop;
}

function getDocHeight() {
	var D = document;
    return Math.max(
		Math.max(D.body.scrollHeight, D.documentElement.scrollHeight),
		Math.max(D.body.offsetHeight, D.documentElement.offsetHeight),
		Math.max(D.body.clientHeight, D.documentElement.clientHeight)
    );
}

function getWS() {
	var winW = 630, winH = 460;
	if (document.body && document.body.offsetWidth) {
		winW = document.body.offsetWidth;
		winH = document.body.offsetHeight;
	}
	if (document.compatMode == 'CSS1Compat' && document.documentElement && document.documentElement.offsetWidth) {
		winW = document.documentElement.offsetWidth;
		winH = document.documentElement.offsetHeight;
	}
	if (window.innerWidth && window.innerHeight) {
		winW = window.innerWidth;
		winH = window.innerHeight;
	}
	return [winW, winH];
}

function val_email(s) {
	return /^[a-zA-Z0-9]([\w\.-]*[a-zA-Z0-9])?@[a-zA-Z0-9][\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]$/.test(s);
}

function val_phone(s) {
	return /^[0-9]+$/.test(s);
}

function val_login(s) {
	return /^[a-zA-Z0-9\_]+$/.test(s);
}

function resized_s(w, h, tw, th) {
	tw = Math.min(tw, w);
	th = Math.min(th, h);
	src_ratio = w / h;
	dst_ratio = tw / th;
	if (src_ratio > dst_ratio)
		th = tw / src_ratio;
	else
		tw = th * src_ratio;

	return [Math.round(tw), Math.round(th)];
}

function get_option(nl) {
	for (var i = 0; i < nl.length; i++) {
		if (nl[i].checked)
			return nl[i].value;
	}
	return false;
}

String.prototype.trim = function(mask) {
	var mask = mask || '\\s';
	var Re = new RegExp('^['+mask+']+|['+mask+']+$', 'g');
	return this.replace(Re, '');
};

String.prototype.makeURL = function() {
	var s = this.replace(/'/g, '');
	s = s.trim();
	s = s.replace(/[^\w]+/g, '-');
	s = s.toLowerCase();
	s = s.trim('-');
	return s;
}

var Prototype = {
	Version: '1.4.0_pre10_ajax',
	
	emptyFunction: function() {},
	K: function(x) {return x}
}

var Class = {
	create: function() {
		return function() { 
			this.initialize.apply(this, arguments);
		}
	}
}

var Abstract = new Object();

Object.extend = function(destination, source) {
	for (property in source) {
		destination[property] = source[property];
	}
	return destination;
}

Object.inspect = function(object) {
	try {
		if (object == undefined) return 'undefined';
		if (object == null) return 'null';
		return object.inspect ? object.inspect() : object.toString();
	} catch (e) {
		if (e instanceof RangeError) return '...';
		throw e;
	}
}

Function.prototype.bind = function(object) {
	var __method = this;
	return function() {
		return __method.apply(object, arguments);
	}
}

Function.prototype.bindAsEventListener = function(object) {
	var __method = this;
	return function(event) {
		return __method.call(object, event || window.event);
	}
}

Object.extend(Number.prototype, {
	toColorPart: function() {
		var digits = this.toString(16);
		if (this < 16) return '0' + digits;
		return digits;
	},

	succ: function() {
		return this + 1;
	},
	
	times: function(iterator) {
		$R(0, this, true).each(iterator);
		return this;
	}
});

var Try = {
	these: function() {
		var returnValue;

		for (var i = 0; i < arguments.length; i++) {
			var lambda = arguments[i];
			try {
				returnValue = lambda();
				break;
			} catch (e) {}
		}

		return returnValue;
	}
}


var PeriodicalExecuter = Class.create();
PeriodicalExecuter.prototype = {
	initialize: function(callback, frequency) {
		this.callback = callback;
		this.frequency = frequency;
		this.currentlyExecuting = false;

		this.registerCallback();
	},

	registerCallback: function() {
		setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
	},

	onTimerEvent: function() {
		if (!this.currentlyExecuting) {
			try { 
				this.currentlyExecuting = true;
				this.callback(); 
			} finally { 
				this.currentlyExecuting = false;
			}
		}
	}
}





var Ajax = {
	getTransport: function() {
		return Try.these(
			function() {return new ActiveXObject('Msxml2.XMLHTTP')},
			function() {return new ActiveXObject('Microsoft.XMLHTTP')},
			function() {return new XMLHttpRequest()}
		) || false;
	}
}

Ajax.Base = function() {};
Ajax.Base.prototype = {
	setOptions: function(options) {
		this.options = {
			method:			 'post',
			asynchronous: true,
			parameters:	 ''
		}
		Object.extend(this.options, options || {});
	},

	responseIsSuccess: function() {
		return this.transport.status == undefined
				|| this.transport.status == 0 
				|| (this.transport.status >= 200 && this.transport.status < 300);
	},

	responseIsFailure: function() {
		return !this.responseIsSuccess();
	}
}

Ajax.Request = Class.create();
Ajax.Request.Events = 
	['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];

Ajax.Request.prototype = Object.extend(new Ajax.Base(), {
	initialize: function(url, options) {
		this.transport = Ajax.getTransport();
		this.setOptions(options);
		this.request(url);
	},

	request: function(url) {
		var parameters = this.options.parameters || '';
		if (parameters.length > 0) parameters += '&_=';

		try {
			if (this.options.method == 'get')
				url += '?' + parameters;

			this.transport.open(this.options.method, url,
				this.options.asynchronous);

			if (this.options.asynchronous) {
				this.transport.onreadystatechange = this.onStateChange.bind(this);
				setTimeout((function() {this.respondToReadyState(1)}).bind(this), 10);
			}

			this.setRequestHeaders();

			var body = this.options.postBody ? this.options.postBody : parameters;
			this.transport.send(this.options.method == 'post' ? body : null);

		} catch (e) {
		}
	},

	setRequestHeaders: function() {
		var requestHeaders = 
			['X-Requested-With', 'XMLHttpRequest',
			 'X-Prototype-Version', Prototype.Version];

		if (this.options.method == 'post') {
			requestHeaders.push('Content-type', 
				'application/x-www-form-urlencoded');

			if (this.transport.overrideMimeType)
				requestHeaders.push('Connection', 'close');
		}

		if (this.options.requestHeaders)
			requestHeaders.push.apply(requestHeaders, this.options.requestHeaders);

		for (var i = 0; i < requestHeaders.length; i += 2)
			this.transport.setRequestHeader(requestHeaders[i], requestHeaders[i+1]);
	},

	onStateChange: function() {
		var readyState = this.transport.readyState;
		if (readyState != 1)
			this.respondToReadyState(this.transport.readyState);
	},
	
	evalJSON: function() {
		try {
			var json = this.transport.getResponseHeader('X-JSON'), object;
			object = eval(json);
			return object;
		} catch (e) {
		}
	},

	respondToReadyState: function(readyState) {
		var event = Ajax.Request.Events[readyState];
		var transport = this.transport, json = this.evalJSON();

		if (event == 'Complete')
			(this.options['on' + this.transport.status]
			 || this.options['on' + (this.responseIsSuccess() ? 'Success' : 'Failure')]
			 || Prototype.emptyFunction)(transport, json);

		(this.options['on' + event] || Prototype.emptyFunction)(transport, json, this);

		if (event == 'Complete')
			this.transport.onreadystatechange = Prototype.emptyFunction;
	}
});

Ajax.Updater = Class.create();
Ajax.Updater.ScriptFragment = '(?:<script.*?>)((\n|.)*?)(?:<\/script>)';

Object.extend(Object.extend(Ajax.Updater.prototype, Ajax.Request.prototype), {
	initialize: function(container, url, options) {
		this.containers = {
			success: container.success ? $(container.success) : $(container),
			failure: container.failure ? $(container.failure) :
				(container.success ? null : $(container))
		}

		this.transport = Ajax.getTransport();
		this.setOptions(options);

		var onComplete = this.options.onComplete || Prototype.emptyFunction;
		this.options.onComplete = (function(transport, object) {
			this.updateContent();
			onComplete(transport, object);
		}).bind(this);

		this.request(url);
	},

	updateContent: function() {
		var receiver = this.responseIsSuccess() ?
			this.containers.success : this.containers.failure;

		var match		= new RegExp(Ajax.Updater.ScriptFragment, 'img');
		var response = this.transport.responseText.replace(match, '');
		var scripts	= this.transport.responseText.match(match);

		if (receiver) {
			if (this.options.insertion) {
				new this.options.insertion(receiver, response);
			} else {
				receiver.innerHTML = response;
			}
		}

		if (this.responseIsSuccess()) {
			if (this.onComplete)
				setTimeout(this.onComplete.bind(this), 10);
		}

		if (this.options.evalScripts && scripts) {
			match = new RegExp(Ajax.Updater.ScriptFragment, 'im');
			setTimeout((function() {
				for (var i = 0; i < scripts.length; i++)
					eval(scripts[i].match(match)[1]);
			}).bind(this), 10);
		}
	}
});

Ajax.PeriodicalUpdater = Class.create();
Ajax.PeriodicalUpdater.prototype = Object.extend(new Ajax.Base(), {
	initialize: function(container, url, options) {
		this.setOptions(options);
		this.onComplete = this.options.onComplete;

		this.frequency = (this.options.frequency || 2);
		this.decay = 1;

		this.updater = {};
		this.container = container;
		this.url = url;

		this.start();
	},

	start: function() {
		this.options.onComplete = this.updateComplete.bind(this);
		this.onTimerEvent();
	},

	stop: function() {
		this.updater.onComplete = undefined;
		clearTimeout(this.timer);
		(this.onComplete || Ajax.emptyFunction).apply(this, arguments);
	},

	updateComplete: function(request) {
		if (this.options.decay) {
			this.decay = (request.responseText == this.lastText ? 
				this.decay * this.options.decay : 1);

			this.lastText = request.responseText;
		}
		this.timer = setTimeout(this.onTimerEvent.bind(this), 
			this.decay * this.frequency * 1000);
	},

	onTimerEvent: function() {
		this.updater = new Ajax.Updater(this.container, this.url, this.options);
	}
});

function $() {
	var elements = new Array();

	for (var i = 0; i < arguments.length; i++) {
		var element = arguments[i];
		if (typeof element == 'string')
			element = document.getElementById(element);

		if (arguments.length == 1) 
			return element;

		elements.push(element);
	}

	return elements;
}