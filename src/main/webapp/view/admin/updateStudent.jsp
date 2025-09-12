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
                    <li class="nav-item active">
                        <span class="active__subItem"></span>
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
                    <!-- Students Tab -->
                    <div>
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h2>Quản lý thông tin sinh viên</h2>
                            <div class="export-btn__container">
                                <form method="get" action="admin">
                                    <input type="hidden" name="action" value="exportStudents" />
                                    <button type="submit" class="export-btn">
                                        <i class="fa-solid fa-file-arrow-down"></i> Xuất Excel
                                    </button>
                                </form>
                            </div>
                            <form method="get" action="admin">
                                <input type="hidden" name="action" value="addStudentForm" />
                                <button class="btn btn-primary" type="submit">
                                    <i class="fas fa-plus me-1"></i>
                                    Thêm sinh viên
                                </button>
                            </form>
                        </div>
                        <div class="card">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <span>Danh sách sinh viên</span>
                                <div style="margin: 0px 30px 0px auto;">
                                    <span style="margin-right: 5px; font-weight: normal;">
                                        Xóa sinh viên đã chọn
                                    </span>
                                    <button type="button" id="deleteTriggerBtn" class="btn btn-outline-secondary">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </div>
                                <div class="filter-controls" style="margin-right: 30px;">
                                    <span style="margin-right: 5px; font-weight: normal;" class="filter-controls__text">
                                        Bộ lọc
                                    </span>
                                    <button class="btn btn-outline-secondary" id="filterBtn">
                                        <i class="fa-solid fa-filter"></i>
                                    </button>
                                </div>
                                <form method="get" action="admin">
                                    <div style="display: flex;">
                                        <input type="hidden" name="action" value="searchStudent" />
                                        <input
                                            style="outline: none; border: none; border-bottom-left-radius: 6px; border-top-left-radius: 6px; padding-left: 10px;"
                                            type="text" name="studentID" placeholder="Nhập mã sinh viên..." required />
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
                                            <input type="hidden" name="action" value="deleteStudents">
                                            <input type="hidden" id="randomCodeInput" name="randomCode">
                                            <input type="hidden" id="deleteCodeConfirm" name="deleteCodeConfirm">
                                            <table class="table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox" id="checkAll" onclick="toggle(this)">
                                                        </th>
                                                        <th>STT</th>
                                                        <th>Mã SV</th>
                                                        <th>Họ và tên</th>
                                                        <th>Giới tính</th>
                                                        <th>Ngày sinh</th>
                                                        <th>Email</th>
                                                        <th>Quê quán</th>
                                                        <th>Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="s" items="${studentList}" varStatus="loop">
                                                        <tr>
                                                            <td><input type="checkbox" name="studentIds"
                                                                    value="${s.studentID}"></td>
                                                            <td>${loop.index + 1}</td>
                                                            <td>${s.studentID}</td>
                                                            <td>${s.name}</td>
                                                            <td>${s.gender}</td>
                                                            <td>${s.dobFormatted}</td>
                                                            <td>${s.email}</td>
                                                            <td>${s.hometown}</td>
                                                            <td>
                                                                <div style="display: flex; gap: 5px;">
                                                                    <a class="btn btn-sm btn-warning"
                                                                        href="admin?action=updateStudentForm&studentID=${s.studentID}">
                                                                        <i class="fas fa-edit"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-danger"
                                                                        href="admin?action=deleteStudentForm&studentID=${s.studentID}">
                                                                        <i class="fas fa-trash"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-info"
                                                                        href="admin?action=searchClassroomListByStudentID&studentID=${s.studentID}">
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
                                            <a class="firstPageBtn" href="admin?action=studentList&page=1">« First</a>
                                        </c:if>
            
                                        <!-- Nút Prev -->
                                        <c:if test="${currentPage > 1}">
                                            <a class="PrevPageBtn" href="admin?action=studentList&page=${currentPage - 1}">‹
                                                Prev</a>
                                        </c:if>
            
                                        <!-- Các trang trong nhóm -->
                                        <%
                                            int notCurrentPageCnt = -1;
                                            for (int i = groupStart; i <= groupEnd; i++) {
                                                notCurrentPageCnt++;
                                        %>
                                            <a href="admin?action=studentList&page=<%=i%>"
                                            class="<%= (i == currentPage) ? "current" : "notCurrentPage" + notCurrentPageCnt %>">
                                                <%=i%>
                                            </a>
                                        <%
                                            }
                                        %>
            
                                        <!-- Nút Next -->
                                        <c:if test="${currentPage < totalPages}">
                                            <a class="nextPageBtn"
                                                href="admin?action=studentList&page=${currentPage + 1}">Next
                                                ›</a>
                                        </c:if>
    
                                        <!-- Nút tới trang cuối -->
                                        <c:if test="${currentPage < totalPages}">
                                            <a class="lastPageBtn" href="admin?action=studentList&page=${totalPages}">Last
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
    <!-- Edit Student Modal -->
    <div class="modal fade" id="editStudentModal" tabindex="-1" aria-labelledby="editStudentModalLabel"
        aria-hidden="true" style="position: fixed; top: 10%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Chỉnh sửa thông tin sinh viên <strong>
                            <c:out value="${student.studentID}" />
                        </strong></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <c:if test="${not empty message}">
                        <div class="alert-error">
                            &#9888; ${message}
                        </div>
                    </c:if>
                    <form action="admin" method="post">
                        <input type="hidden" name="action" value="updateStudent" />
                        <input type="hidden" name="studentID" value="${student.studentID}">
                        <div class="mb-3">
                            <label for="name" class="form-label">Họ tên</label>
                            <input id="name" class="form-control" type="text" name="name" value="${student.name}"
                                required>
                        </div>
                        <div class="mb-3">
                            <label for="gender" class="form-label">Giới tính</label>
                            <select class="form-select" id="gender" name="gender" required>
                                <option value="">Chọn giới tính</option>
                                <option value="Nam" ${student.gender=='Nam' ? 'selected' : '' }>Nam</option>
                                <option value="Nữ" ${student.gender=='Nữ' ? 'selected' : '' }>Nữ</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="dob" class="form-label">Ngày sinh</label>
                            <input id="dob" class="form-control" type="date" name="dob" value="${student.dob}" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input id="email" class="form-control" type="email" name="email" value="${student.email}"
                                required>
                        </div>
                        <div class="mb-3">
                            <label for="hometown" class="form-label">Quê quán</label>
                            <input id="hometown" class="form-control" type="text" name="hometown"
                                value="${student.hometown}" required>
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
            var myModal = new bootstrap.Modal(document.getElementById('editStudentModal'));
            myModal.show();
        });
    </script>

    <div style="cursor: pointer;" id="overlay" class="filter-overlay"></div>
    <div id="filterModal" class="filter-modal">
        <h3>Bộ lọc sinh viên</h3>
        <form action="admin" method="get">
            <input type="hidden" name="action" value="studentFilter" />
            <div class="mb-3">
                <label for="gender" class="form-label">Giới tính</label>
                <select class="form-select" id="gender" name="gender">
                    <option value="">Chọn giới tính</option>
                    <option value="Nam">Nam</option>
                    <option value="Nữ">Nữ</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="yob" class="form-label">Năm sinh</label>
                <select class="form-select" id="yob" name="yob">
                    <option value="">Chọn năm sinh</option>
                    <c:forEach var="yob" items="${yobList}">
                        <option value="${yob}">${yob}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="hometown" class="form-label">Quê quán</label>
                <select class="form-select" id="hometown" name="hometown">
                    <option value="">Chọn quê quán</option>
                    <c:forEach var="hometown" items="${hometownList}">
                        <option value="${hometown}">${hometown}</option>
                    </c:forEach>
                </select>
            </div>
            <div style="display: flex; flex-direction: row-reverse;">
                <button style="margin-left: 10px;" type="submit" class="btn btn-primary">Lưu</button>
                <button id="cancel-filter__btn" type="button" class="btn btn-secondary"
                    data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
    <script>
        const filterBtn = document.getElementById('filterBtn');
        const filterModal = document.getElementById('filterModal');
        const overlay = document.getElementById('overlay');
        const cancelFilter__btn = document.getElementById('cancel-filter__btn');

        filterBtn.addEventListener('click', () => {
            filterModal.style.display = 'block';
            overlay.style.display = 'block';
        });

        overlay.addEventListener('click', () => {
            filterModal.style.display = 'none';
            overlay.style.display = 'none';
        });

        cancelFilter__btn.addEventListener('click', () => {
            filterModal.style.display = 'none';
            overlay.style.display = 'none';
        });
    </script>
    <script>
        function toggle(source) {
            checkboxes = document.getElementsByName('studentIds');
            for (var i = 0; i < checkboxes.length; i++) {
                checkboxes[i].checked = source.checked;
            }
        }
    </script>
    <div class="modal fade" id="deleteStudentsModal" tabindex="-1" aria-labelledby="deleteStudentsModalLabel"
        aria-hidden="true" style="position: fixed; top: 10%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">Xóa sinh viên</h5>
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
            var myModal = new bootstrap.Modal(document.getElementById('deleteStudentsModal'));
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