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
                    <li class="nav-item active">
                        <span class="active__subItem"></span>
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
                    <li class="nav-item">
                        <form method="get" action="admin">
                            <input type="hidden" name="action" value="student_classroom">
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
                    <!-- Classrooms Tab -->
                    <div>
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h2>Quản lý thông tin lớp học</h2>
                            <div class="export-btn__container">
                                <form method="get" action="admin">
                                    <input type="hidden" name="action" value="exportClassrooms" />
                                    <button type="submit" class="export-btn">
                                        <i class="fa-solid fa-file-arrow-down"></i> Xuất Excel
                                    </button>
                                </form>
                            </div>
                            <form method="get" action="admin">
                                <input type="hidden" name="action" value="addClassroomForm" />
                                <button class="btn btn-primary" type="submit">
                                    <i class="fas fa-plus me-1"></i>
                                    Thêm lớp học
                                </button>
                            </form>
                        </div>
                        <div class="card">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <span>Danh sách lớp học</span>
                                <div style="margin: 0px 30px 0px auto;">
                                    <span style="margin-right: 5px; font-weight: normal;">
                                        Xóa lớp học đã chọn
                                    </span>
                                    <button type="button" id="deleteTriggerBtn" class="btn btn-outline-secondary">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </div>
                                <form method="get" action="admin">
                                    <div style="display: flex;">
                                        <input type="hidden" name="action" value="searchClassroom" />
                                        <input
                                            style="outline: none; border: none; border-bottom-left-radius: 6px; border-top-left-radius: 6px; padding-left: 10px;"
                                            type="text" name="classroomID" placeholder="Nhập mã lớp học..." required/>
                                        <button class="btn btn-outline-secondary" type="submit">
                                            <i class="fas fa-search"></i>
                                        </button>
                                    </div>
                                </form>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <div style="max-height: 520.5px; overflow: auto;">
                                        <form method="post" action="admin">
                                            <input type="hidden" name="action" value="deleteClassrooms">
                                            <input type="hidden" id="randomCodeInput" name="randomCode">
                                            <input type="hidden" id="deleteCodeConfirm" name="deleteCodeConfirm">
                                            <table class="table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox" id="checkAll" onclick="toggle(this)">
                                                            </th>
                                                        <th>STT</th>
                                                        <th>Mã lớp</th>
                                                        <th>Tên lớp</th>
                                                        <th>Mã GV</th>
                                                        <th>Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="c" items="${classroomList}" varStatus="loop">
                                                        <tr>
                                                            <td><input type="checkbox" name="classroomIds"
                                                                        value="${c.classroomID}"></td>
                                                            <td>${loop.index + 1}</td>
                                                            <td>${c.classroomID}</td>
                                                            <td>${c.name}</td>
                                                            <td>${c.teacherID}</td>
                                                            <td>
                                                                <div style="display: flex; gap: 5px;">
                                                                    <a class="btn btn-sm btn-warning"
                                                                        href="admin?action=updateClassroomForm&classroomID=${c.classroomID}">
                                                                        <i class="fas fa-edit"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-danger"
                                                                        href="admin?action=deleteClassroomForm&classroomID=${c.classroomID}">
                                                                        <i class="fas fa-trash"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-info"
                                                                        href="admin?action=searchStudentListByClassroomID&classroomID=${c.classroomID}">
                                                                        <i class="fas fa-eye"></i>
                                                                    </a>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <button id="submitDeleteBtn" style="display: none;" type="submit"></button>
                                        </form>
                                    </div>
                                    <%
                                        int groupSize = 5;
                                        int currentPage = (Integer) request.getAttribute("currentPage");
                                        int totalPages = (Integer) request.getAttribute("totalPages");

                                        int groupStart = ((currentPage - 1) / groupSize) * groupSize + 1;
                                        int groupEnd = Math.min(groupStart + groupSize - 1, totalPages);
                                    %>
            
                                    <div class="pagination">
                                        <!-- Nút về trang đầu -->
                                        <c:if test="${currentPage > 1}">
                                            <a class="firstPageBtn" href="admin?action=classroomList&page=1">« First</a>
                                        </c:if>
            
                                        <!-- Nút Prev -->
                                        <c:if test="${currentPage > 1}">
                                            <a class="PrevPageBtn" href="admin?action=classroomList&page=${currentPage - 1}">‹
                                                Prev</a>
                                        </c:if>
            
                                        <!-- Các trang trong nhóm -->
                                        <%
                                            int notCurrentPageCnt = -1;
                                            for (int i = groupStart; i <= groupEnd; i++) {
                                                notCurrentPageCnt++;
                                        %>
                                            <a href="admin?action=classroomList&page=<%=i%>"
                                            class="<%= (i == currentPage) ? "current" : "notCurrentPage" + notCurrentPageCnt %>">
                                                <%=i%>
                                            </a>
                                        <%
                                            }
                                        %>
            
                                        <!-- Nút Next -->
                                        <c:if test="${currentPage < totalPages}">
                                            <a class="nextPageBtn"
                                                href="admin?action=classroomList&page=${currentPage + 1}">Next
                                                ›</a>
                                        </c:if>
    
                                        <!-- Nút tới trang cuối -->
                                        <c:if test="${currentPage < totalPages}">
                                            <a class="lastPageBtn" href="admin?action=classroomList&page=${totalPages}">Last
                                                »</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Delete Classroom Modal -->
    <div class="modal fade" id="deleteClassroomModal" tabindex="-1" aria-labelledby="deleteClassroomModalLabel"
        aria-hidden="true" style="position: fixed; top: 10%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">Xóa lớp học</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <c:if test="${not empty message}">
                        <div class="alert-error">
                            &#9888; ${message}
                        </div>
                    </c:if>
                    <p>Bạn có chắc chắn muốn xóa lớp học <strong>
                            <c:out value="${classroomID}" />
                        </strong> không?</p>
                    <p class="text-danger">Hành động này không thể hoàn tác!</p>
                </div>
                <div class="modal-footer">
                    <form action="admin" method="post">
                        <input type="hidden" name="action" value="deleteClassroom" />
                        <input type="hidden" name="classroomID" value="${classroomID}" />
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-danger">Xóa</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
        window.addEventListener('DOMContentLoaded', (event) => {
            var myModal = new bootstrap.Modal(document.getElementById('deleteClassroomModal'));
            myModal.show();
        });
    </script>
    <script>
        function toggle(source) {
            checkboxes = document.getElementsByName('classroomIds');
            for (var i = 0; i < checkboxes.length; i++) {
                checkboxes[i].checked = source.checked;
            }
        }
    </script>
    <div class="modal fade" id="deleteClassroomsModal" tabindex="-1" aria-labelledby="deleteClassroomsModalLabel"
        aria-hidden="true" style="position: fixed; top: 10%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">Xóa lớp học</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" style="display: flex; flex-direction: column;">
                    <p>Nhập lại mã sau để xác nhận xóa:
                        <strong id="randomCode"></strong>
                    </p>
                    <input
                        style="border-radius: 5px; border: 1px solid rgba(0, 0, 0, 0.3); margin-bottom: 15px; outline: none; padding: 5px 0px 5px 10px;"
                        type="text" id="deleteCodeConfirmInput" placeholder="Nhập mã xác nhận" required />
                    <p class="text-danger">Hành động này không thể hoàn tác!</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button id="deleteTriggerBtn2" class="btn btn-danger">Xóa</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        document.getElementById("deleteTriggerBtn").addEventListener("click", function () {
            var myModal = new bootstrap.Modal(document.getElementById('deleteClassroomsModal'));
            myModal.show();
            showDeleteConfirm();
        });
    </script>
    <script>
        document.getElementById("deleteTriggerBtn2").addEventListener("click", function () {
            document.getElementById("deleteCodeConfirm").value = document.getElementById("deleteCodeConfirmInput").value;
            document.getElementById("submitDeleteBtn").click();
        });
    </script>
    <script>
        let generatedCode = "";

        function generateRandomCode(length = 6) {
            const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
            let code = '';
            for (let i = 0; i < length; i++) {
                code += chars.charAt(Math.floor(Math.random() * chars.length));
            }
            return code;
        }

        function showDeleteConfirm() {
            generatedCode = generateRandomCode();
            document.getElementById("randomCode").textContent = generatedCode;
            document.getElementById("randomCodeInput").value = generatedCode;
        }
    </script>
</body>

</html>