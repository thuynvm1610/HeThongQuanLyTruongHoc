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
</head>

<body>
    <c:if test="${not empty message}">
        <div class="alert-error">
            &#9888; ${message}
        </div>
    </c:if>
    <c:if test="${not empty succeedAddMessage}">
        <div class="alert-success">
            &#9989; ${succeedAddMessage}
        </div>
    </c:if>
    <c:if test="${not empty succeedDeleteMessage}">
        <div class="alert-success">
            &#9989; ${succeedDeleteMessage}
        </div>
    </c:if>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-2 sidebar p-0">
                <div class="text-left p-3 bg-dark">
                    <div class="logo">SICT<span class="sub"> Portal</span></div>
                </div>
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <form method="get" action="admin">
                            <input type="hidden" name="action" value="dashboard">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-tachometer-alt me-2"></i>
                                </div>
                                Dashboard
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form method="get" action="admin">
                            <input type="hidden" name="action" value="accountList">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-users me-2"></i>
                                </div>
                                Tài khoản người dùng
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form method="get" action="admin">
                            <input type="hidden" name="action" value="studentList">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-user-graduate me-2"></i>
                                </div>
                                Thông tin sinh viên
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form method="get" action="admin">
                            <input type="hidden" name="action" value="teacherList">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-chalkboard-teacher me-2"></i>
                                </div>
                                Thông tin giáo viên
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form method="get" action="admin">
                            <input type="hidden" name="action" value="classroomList">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-school me-2"></i>
                                </div>
                                Thông tin lớp học
                            </button>
                        </form>
                    </li>
                    <li class="nav-item active">
                        <span class="active__subItem"></span>
                        <form method="get" action="admin">
                            <input type="hidden" name="action" value="classroomList">
                            <button type="submit" class="sidebar-btn">
                                <div class="sidebar__icon-container">
                                    <i class="fas fa-user-plus me-2"></i>
                                </div>
                                Sinh viên - Lớp học
                            </button>
                        </form>
                    </li>
                    <li class="nav-item mt-3">
                        <form method="get" action="admin">
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
                    <!-- Student - Classroom Tab -->
                    <div>
                        <h2 class="mb-4">Sinh viên - Lớp học</h2>
                        <div class="row">
                            <div class="col-md-6">
                                <form method="get" action="admin">
                                    <input type="hidden" name="action" value="searchStudentListByClassroomID" />
                                    <label for="classroomID" class="form-label">Xem danh sách sinh viên theo mã
                                        lớp</label>
                                    <div class="input-group" style="box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.5); border-radius: 6px;">
                                        <input type="text" class="form-control" id="classroomID" name="classroomID"
                                            style="box-shadow: none;" placeholder="Nhập mã lớp...">
                                        <button class="btn btn-primary"><i class="fas fa-search"></i></button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6">
                                <form method="get" action="admin">
                                    <input type="hidden" name="action" value="searchClassroomListByStudentID" />
                                    <label for="studentID" class="form-label">Xem danh sách lớp theo mã sinh viên</label>
                                    <div class="input-group" style="box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.5); border-radius: 6px;">
                                        <input type="text" class="form-control" id="studentID" name="studentID"
                                            style="box-shadow: none;" placeholder="Nhập mã sinh viên...">
                                        <button class="btn btn-primary"><i class="fas fa-search"></i></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="row" style="border-top: 1px solid black; margin-top: 24px;">
                            <div class="col-md-6" style="margin-top: 24px;">
                                <form method="post" action="admin">
                                    <input type="hidden" name="action" value="addStudent_classroom" />
                                    <label style="font-size: 18.72px; font-weight: bold;" for="classroomID"
                                        class="form-label text-center w-100">
                                        Thêm sinh viên vào lớp
                                    </label>
                                    <div class="input-group" style="box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.5); border-radius: 6px;">
                                        <input type="text" class="form-control" id="classroomID" name="classroomID"
                                            value="${classroomID}" style="box-shadow: none;"
                                            placeholder="Nhập mã lớp...">
                                        <input type="text" class="form-control" id="studentID" name="studentID"
                                            value="${studentID}" style="box-shadow: none;" placeholder="Nhập mã SV...">
                                    </div>
                                    <div class="d-flex justify-content-end" style="margin-top: 16px;">
                                        <button type="submit" class="btn btn-success btn-sm">
                                            <i class="fas fa-user-plus"></i> Thêm sinh viên
                                        </button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6" style="margin-top: 24px;">
                                <form method="post" action="admin">
                                    <input type="hidden" name="action" value="deleteStudent_classroom" />
                                    <label style="font-size: 18.72px; font-weight: bold" for="studentID"
                                        class="form-label text-center w-100">
                                        Xóa sinh viên khỏi lớp
                                    </label>
                                    <div class="input-group" style="box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.5); border-radius: 6px;">
                                        <input type="text" class="form-control" id="studentID" name="studentID"
                                            value="${studentID}" style="box-shadow: none;" placeholder="Nhập mã SV...">
                                        <input type="text" class="form-control" id="classroomID" name="classroomID"
                                            value="${classroomID}" style="box-shadow: none;"
                                            placeholder="Nhập mã lớp...">
                                    </div>
                                    <div class="d-flex justify-content-end" style="margin-top: 16px;">
                                        <button type="submit" class="btn btn-danger btn-sm">
                                            <i class="fas fa-user-minus"></i> Xoá sinh viên
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>