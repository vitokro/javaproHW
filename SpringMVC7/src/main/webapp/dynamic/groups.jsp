<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prog.kiev.ua</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <h3><img height="50" width="55" src="<c:url value="/static/logo.png"/>"/><a href="/">Groups List</a></h3>

    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul id="groupList" class="nav navbar-nav">
                    <li><button type="button" id="add_group" class="btn btn-default navbar-btn">Add Group</button></li>
                    <li><button type="button" id="delete_group" class="btn btn-default navbar-btn">Delete Group</button></li>
                    <li><button type="button" id="contacts_list" class="btn btn-default navbar-btn">Contacts List</button></li>
                </ul>
                <form class="navbar-form navbar-left" role="search" action="/group_search" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" name="pattern" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <table class="table table-striped">
        <thead>
        <tr>
            <td></td>
            <td><b>Name</b></td>
        </tr>
        </thead>
        <c:forEach items="${groups}" var="group">
            <tr>
                <td><input type="checkbox" name="toDelete[]" value="${group.id}" id="checkbox_${group.id}"/></td>
                <td>${group.name}</td>
            </tr>
        </c:forEach>
    </table>

    <nav aria-label="Page navigation">
        <ul class="pagination">
            <c:forEach var="i" begin="1" end="${pages}">
                <li><a href="/?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
            </c:forEach>
        </ul>
    </nav>
</div>

<script>
    $('.dropdown-toggle').dropdown();

    $('#add_group').click(function(){
        window.location.href='/group_add_page';
    });

    $('#contacts_list').click(function(){
        window.location.href='/';
    });

    $('#delete_group').click(function(){
        var data = { 'toDelete[]' : []};
        $(":checked").each(function() {
            data['toDelete[]'].push($(this).val());
        });
        $.post("/group/delete", data, function(data, status) {
            window.location.reload();
        });
    });

</script>
</body>
</html>