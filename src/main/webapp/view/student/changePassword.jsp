<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hệ thống quản lý trường SICT</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <style>
        .user-profile {
            display: flex;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
            object-fit: cover;
        }

        .user-info {
            color: white;
        }
    </style>
</head>

<body>
    <c:if test="${not empty succeedAddMessage}">
        <div class="alert-success">
            &#9989; ${succeedAddMessage}
        </div>
    </c:if>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-2 sidebar p-0">
                <div class="user-profile bg-dark">
                    <img src="https://png.pngtree.com/png-vector/20191027/ourmid/pngtree-user-icon-isolated-on-abstract-background-png-image_1875037.jpg"
                        alt="User Avatar" class="user-avatar">
                    <div class="user-info">
                        <h6 class="user-name" id="sidebarUserName">${studentName}</h6>
                        <span class="user-role" id="sidebarUserRole">Sinh viên</span>
                    </div>
                </div>
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <form method="get" action="student">
                            <input type="hidden" name="action" value="personalInformation">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-id-card me-2"></i>
                                </div>
                                Thông tin cá nhân
                            </button>
                        </form>
                    </li>
                    <li class="nav-item active">
                        <span class="active__subItem"></span>
                        <form method="get" action="student">
                            <input type="hidden" name="action" value="accountInformation">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-user-cog me-2"></i> 
                                </div>
                                Thông tin tài khoản
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form method="get" action="student">
                            <input type="hidden" name="action" value="searchClassroomListByStudentID">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-school me-2"></i> 
                                </div>
                                Danh sách lớp học
                            </button>
                        </form>
                    </li>
                    <li class="nav-item mt-3">
                        <form method="get" action="student">
                            <input type="hidden" name="action" value="logout">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-sign-out-alt me-2"></i>
                                </div>
                                Đăng xuất
                            </button>
                        </form>
                    </li>
                </ul>
            </div>
            <!-- Main content -->
            <div class="col-md-10 main-content">
                <div class="tab-content">
                    <!-- Account Information Tab -->
                    <h2 class="mb-4">Thông tin tài khoản</h2>
                    <div class="card">
                        <div class="card-header">
                            <span>Thông tin tài khoản</span>
                        </div>
                        <div class="card-body">
                            <form method="get" action="student">
                                <div class="row">
                                    <input type="hidden" name="action" value="changePasswordForm">
                                    <input type="hidden" name="accountID" value="${account.accountID}">
                                    <input type="hidden" name="role" value="${account.role}">
                                    <input type="hidden" name="studentID" value="${account.studentID}">
                                    <div class="col-md-6 mb-3">
                                        <label for="username" class="form-label">Tên tài khoản</label>
                                        <input type="text" class="form-control" id="username" name="username"
                                            value="${account.username}" style="box-shadow: none;" readonly>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="password" class="form-label">Mật khẩu</label>
                                        <input type="text" class="form-control" id="password" name="password"
                                            value="${account.password}" style="box-shadow: none;" readonly>
                                    </div>
                                    <div class="col-12">
                                        <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Account Password Modal -->
    <div class="modal fade" id="editAccountPasswordModal" tabindex="-1" aria-labelledby="editAccountPasswordModalLabel" aria-hidden="true"
        style="position: fixed; top: 10%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Đổi mật khẩu</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <c:if test="${not empty message}">
                        <div class="alert-error">
                            &#9888; ${message}
                        </div>
                    </c:if>
                    <form action="student" method="post">
                        <input type="hidden" name="action" value="changePassword" />
                        <input type="hidden" name="accountID" value="${account.accountID}">
                        <input type="hidden" name="username" value="${account.username}">
                        <input type="hidden" name="role" value="${account.role}">
                        <input type="hidden" name="studentID" value="${account.studentID}">
                        <div class="mb-3">
                            <label for="password" class="form-label">Mật khẩu</label>
                            <input id="password" class="form-control" type="text" name="password" value="${account.password}"
                                required>
                        </div>
                        <div style="display: flex; flex-direction: row-reverse;">
                            <button style="margin-left: 10px;" type="submit" class="btn btn-primary">Lưu</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        window.addEventListener('DOMContentLoaded', (event) => {
            var myModal = new bootstrap.Modal(document.getElementById('editAccountPasswordModal'));
            myModal.show();
        });
    </script>
</body>

</html>