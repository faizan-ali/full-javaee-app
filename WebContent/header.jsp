<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <meta name="description" content="WorkAmerica">
        <meta name="author" content="Faizan Ali">
        <link rel="icon" href="favicon.ico">

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Site-wide custom CSS -->
        <link href="css/site-wide.css" rel="stylesheet">

        <!-- jQuery Library -->
        <script src="js/jquery-1.12.0.min.js"></script>

        <!-- Bootstrap core JavaScript -->
        <script type="text/javascript" src="js/bootstrap.min.js"></script>

        <!-- AngularJS -->
        <script src="js/angular1.5.0.min.js"></script>

        <!-- Simple-Icons Font -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.2.3/css/simple-line-icons.css">

        <!-- Select2 JS Library and CSS -->
        <link href="css/select2.min.css" rel="stylesheet" />
        <script src="js/select2.min.js"></script>

        <script src='https://cdn.slaask.com/chat.js'></script>

        <c:choose>
            <c:when test="${not empty user}">
                <script>
                    _slaask.identify('UserName', {
                        id: '${user.clientID}',
                        email: '${user.email}',
                        name: '${user.firstName} ${user.lastName}',
                        company: '${user.company}'
                    });
                    _slaask.init('0b75a46e0da9477b6cdaeafd34d8ce2e');
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    _slaask.init('0b75a46e0da9477b6cdaeafd34d8ce2e');
                </script>
            </c:otherwise>
        </c:choose>

        <script>
            (function (i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                    m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-38337436-3', 'auto');
            ga('require', 'linkid');
            ga('send', 'pageview');
        </script>

        <c:if test='${not empty user}'>
            <script>
                ga('set', 'userId', '${user.clientID}');
            </script>
        </c:if>


        </head>



        <body>
            <div id="wrapper">
                <!-- Navigation bar -->
                <nav class="navbar navbar-default" role="navigation">
                    <div class="container-fluid hidden-xs hidden-sm">
                        <div class="navbar-header">
                            <!-- WorkAmerica Logo -->
                            <a class="navbar-brand" href="landing.jsp">
                                <img class="img-responsive" src="img/LOGO.png" style="margin-left: 10px; margin-top: 10px;" width="180" height="30">
                            </a>

                            <p class="navbar-text archer" style="color: #e01b3b !important;">Find In-Demand Talent</p>
                        </div>
                    </div>
                    <div class="container visible-xs visible-sm">
                        <div class="navbar-header">
                            <!-- WorkAmerica Logo -->
                            <img class="img-responsive" src="img/workamerica.png" style="margin: 0 auto; margin-top: 15px;" width="180">
                        </div>
                    </div>
                </nav>