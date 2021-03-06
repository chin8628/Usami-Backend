<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../templates/header.jsp" />

<div class="page-header">
    <h1>Result search user</h1>
</div>

<div class="panel-default panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="table">
                <thead>
                    <tr>
                        <th class="col-md-1">ID</th>
                        <th class="col-md-1"></th>
                        <th>Creator</th>
                        <th>Coin</th>
                        <th class="col-sm-1">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${requestScope.allUser}">
                    <tr>
                        <td>${user.getUsername()}</td>
                        <td>
                            <a href="${SITE_URL}/ViewProfile/?id=${user.getUsername()}">
                                <img src="${SITE_URL}/asset/img/avatar-img/${user.getProfile().getUrl_image()}" class="img-responsive avatar-art">
                            </a>
                        </td>
                        <td><a href="${SITE_URL}/ViewProfile/?id=${user.getUsername()}">${user.getProfile().getFullname()}</a></td>
                        <td>${user.getCoin()}</td>
                        <td class="text-center">
                            <button
                                class="btn btn-default btn-sm"
                                data-toggle="modal"
                                data-target="#${user.getUsername()}-modal"
                                data-user-id="${SITE_URL}"
                                data-user-img="${user.getProfile().getUrl_image()}"
                                data-user-name="${user.getProfile().getFullname()}"
                                data-user-coin="${user.getCoin()}"
                                data-target="#modal">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            </button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<c:if test="${requestScope.allUser != null}">
    <c:forEach var="user" items="${requestScope.allUser}">
<div class="modal fade" tabindex="-1" role="dialog" id="${user.getUsername()}-modal" aria-labelledby="${user.getUsername()}-modalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>Edit Profile</h4>
            </div>
            <div class="modal-body">
                <form method="post" id="pedit-${user.getUsername()}" enctype="multipart/form-data" action="${SITE_URL}/AdminEditProfile/">
                    <input type="hidden" value="${key}" name="key">
                    <input type="hidden" value="${mode}" name="mode">
                    <div class="form-group row">
                        <div class="col-sm-3">
                            <span class="thumbnail">
                              <img src="${SITE_URL}/asset/img/avatar-img/${user.getProfile().getUrl_image()}" class="img-responsive">
                            </span>
                        </div>
                        <div class="col-sm-9">
                            <label for="avatar">Avatar Image</label>
                            <input type="file" class="form-control" id="avatar" name="avatar">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control"  disabled="disabled" value="${user.getProfile().username}">
                        <input type="hidden" class="form-control" id="username" name="username" value="${user.getProfile().username}">
                    </div>
                    <div class="form-group">
                        <label for="firstname">First Name</label>
                        <input type="text" class="form-control" id="firstname" name="firstname" value="${user.getProfile().first_name}">
                    </div>
                    <div class="form-group">
                        <label for="lastname">Last Name</label>
                        <input type="text" class="form-control" id="lastname" name="lastname" value="${user.getProfile().last_name}">
                    </div>
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email" name="email" value="${user.email}">
                    </div>
                    <div class="form-group">
                        <label for="re-password">New-Password</label>
                        <input type="password" class="form-control" id="new-password" placeholder="New-type Password" name="new-password">
                    </div>
                    <div class="form-group">
                        <label for="re-password">Re-Password</label>
                        <input type="password" class="form-control" id="re-password" placeholder="Re-type Password" name="re-password">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-danger" form="delete-user-${comm.getId()}">Delete Account</button>
                <button type="submit" class="btn btn-primary" form="pedit-${user.getUsername()}">Update</button>
                
                <form method="POST" action="${SITE_URL}/AdminDeleteUser" id="delete-user-${comm.getId()}">
                    <input type="hidden" value="${key}" name="key">
                    <input type="hidden" value="${mode}" name="mode">
                    <input type="hidden" value="${user.getUsername()}" name="username">
                </form>
            </div>
        </div>
    </div>
</div>
    </c:forEach>
</c:if>

<script>
    /* Manage table */
    $(document).ready(function(){
        $('#table').DataTable();
    });
</script>

<jsp:include page="../templates/footer.jsp" />
