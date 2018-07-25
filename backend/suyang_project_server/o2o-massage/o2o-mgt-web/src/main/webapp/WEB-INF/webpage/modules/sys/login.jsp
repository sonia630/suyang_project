<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-us" id="extr-page">
<head>
    <meta charset="utf-8">
    <title> SmartAdmin</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <!-- #CSS Links -->
    <!-- Basic Styles -->
    <link rel="stylesheet" type="text/css" media="screen" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="/static/css/font-awesome.min.css">

    <!-- SmartAdmin Styles : Caution! DO NOT change the order -->
    <link rel="stylesheet" type="text/css" media="screen"
          href="/static/css/smartadmin-production-plugins.min.css">
    <link rel="stylesheet" type="text/css" media="screen"
          href="/static/css/smartadmin-production.min.css">
    <link rel="stylesheet" type="text/css" media="screen"
          href="/static/css/smartadmin-skins.min.css">

    <!-- SmartAdmin RTL Support -->
    <link rel="stylesheet" type="text/css" media="screen" href="/static/css/smartadmin-rtl.min.css">

    <!-- We recommend you use "your_style.css" to override SmartAdmin
         specific styles this will also ensure you retrain your customization with each SmartAdmin update.
    <link rel="stylesheet" type="text/css" media="screen" href="css/your_style.css"> -->

    <!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
    <link rel="stylesheet" type="text/css" media="screen" href="/static/css/demo.min.css">

    <!-- #FAVICONS -->
    <link rel="shortcut icon" href="/static/img/favicon/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/static/img/favicon/favicon.ico" type="image/x-icon">
    <style>
        .login {
            position: absolute;
            margin: auto;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
        }
    </style>
</head>

<body class="animated fadeInDown">

<header id="header">

    <div id="logo-group">
    </div>
</header>

<div id="main" role="main">

    <!-- MAIN CONTENT -->
    <div id="content" class="container">

        <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4 login">
            <div class="well no-padding">
                <form id="login-form" class="smart-form client-form" method="post">
                    <header>
                        用户登陆
                    </header>

                    <fieldset>

                        <section>
                            <label class="label">用户名</label>
                            <label class="input"> <i class="icon-append fa fa-user"></i>
                                <input type="text" name="username">
                                <b class="tooltip tooltip-top-right"><i
                                        class="fa fa-user txt-color-teal"></i> Please enter username</b></label>
                        </section>

                        <section>
                            <label class="label">Password</label>
                            <label class="input"> <i class="icon-append fa fa-lock"></i>
                                <input type="password" name="password">
                                <b class="tooltip tooltip-top-right"><i
                                        class="fa fa-lock txt-color-teal"></i> Enter your
                                    password</b> </label>
                        </section>

                        <section>
                            <label class="checkbox">
                                <input type="checkbox" name="remember" checked="">
                                <i></i>Stay signed in</label>
                        </section>
                    </fieldset>
                    <footer>
                        <button type="submit" class="btn btn-primary">
                            登陆
                        </button>
                    </footer>
                </form>

            </div>
        </div>
    </div>

</div>

<!--================================================== -->

<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
<script src="/static/js/plugin/pace/pace.min.js"></script>

<script src="/static/js/libs/jquery-3.2.1.min.js"></script>

<script src="/static/js/app.config.js"></script>

<script src="/static/js/bootstrap/bootstrap.min.js"></script>

<!-- JQUERY VALIDATE -->
<script src="/static/js/plugin/jquery-validate/js/jquery.validate.min.js"></script>

<!-- JQUERY MASKED INPUT -->
<script src="/static/js/plugin/masked-input/jquery.maskedinput.min.js"></script>

<!--[if IE 8]>

<h1>Your browser is out of date, please update your browser by going to
    www.microsoft.com/download</h1>

<![endif]-->

<script>
    $(function () {
        // Validation
        $("#login-form").validate({
                                      // Rules for form validation
                                      rules: {
                                          username: {
                                              required: true,
                                              minlength: 3,
                                              maxlength: 20
                                          },
                                          password: {
                                              required: true,
                                              minlength: 3,
                                              maxlength: 20
                                          }
                                      },

                                      // Messages for form validation
                                      messages: {
                                          username: {
                                              required: 'Please enter your username'
                                          },
                                          password: {
                                              required: 'Please enter your password'
                                          }
                                      },

                                      // Do not change code below
                                      errorPlacement: function (error, element) {
                                          error.insertAfter(element.parent());
                                      }
                                  });
    });
</script>

</body>
</html>