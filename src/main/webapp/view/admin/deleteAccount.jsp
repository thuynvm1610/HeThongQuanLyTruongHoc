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
                    <li class="nav-item active">
                        <span class="active__subItem"></span>
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
                            <h2>Quản lý thông tin tài khoản</h2>
                            <div class="export-btn__container">
                                <form method="get" action="admin">
                                    <input type="hidden" name="action" value="exportAccounts" />
                                    <button type="submit" class="export-btn">
                                        <i class="fa-solid fa-file-arrow-down"></i> Xuất Excel
                                    </button>
                                </form>
                            </div>
                            <form method="get" action="admin">
                                <input type="hidden" name="action" value="addAccountForm" />
                                <button class="btn btn-primary" type="submit">
                                    <i class="fas fa-plus me-1"></i>
                                    Thêm tài khoản
                                </button>
                            </form>
                        </div>
                        <div class="card">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <span>Danh sách tài khoản</span>
                                <div style="margin: 0px 30px 0px auto;">
                                    <span style="margin-right: 5px; font-weight: normal;">
                                        Xóa tài khoản đã chọn
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
                                        <input type="hidden" name="action" value="searchAccount" />
                                        <input
                                            style="outline: none; border: none; border-bottom-left-radius: 6px; border-top-left-radius: 6px; padding-left: 10px;"
                                            type="text" name="accountID" placeholder="Nhập mã tài khoản..." required />
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
                                            <input type="hidden" name="action" value="deleteAccounts">
                                            <input type="hidden" id="randomCodeInput" name="randomCode">
                                            <input type="hidden" id="deleteCodeConfirm" name="deleteCodeConfirm">
                                            <table class="table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox" id="checkAll" onclick="toggle(this)">
                                                        </th>
                                                        <th>STT</th>
                                                        <th>Mã tài khoản</th>
                                                        <th>Tên tài khoản</th>
                                                        <th>Mật khẩu</th>
                                                        <th>Quyền đăng nhập</th>
                                                        <th>Mã SV</th>
                                                        <th>Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="a" items="${accountList}" varStatus="loop">
                                                        <tr>
                                                            <td><input type="checkbox" name="accountIds"
                                                                    value="${a.accountID}"></td>
                                                            <td>${loop.index + 1}</td>
                                                            <td>${a.accountID}</td>
                                                            <td>${a.username}</td>
                                                            <td>${a.password}</td>
                                                            <td>${a.role}</td>
                                                            <td>${a.studentID}</td>
                                                            <td>
                                                                <div style="display: flex; gap: 5px;">
                                                                    <a class="btn btn-sm btn-warning"
                                                                        href="admin?action=updateAccountForm&accountID=${a.accountID}">
                                                                        <i class="fas fa-edit"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-danger"
                                                                        href="admin?action=deleteAccountForm&accountID=${a.accountID}">
                                                                        <i class="fas fa-trash"></i>
                                                                    </a>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            
                                            <button id="submitDeleteBtn" style="display: none;"
                                            type="submit"></button>
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
                                            <a class="firstPageBtn" href="admin?action=accountList&page=1">« First</a>
                                        </c:if>
            
                                        <!-- Nút Prev -->
                                        <c:if test="${currentPage > 1}">
                                            <a class="PrevPageBtn" href="admin?action=accountList&page=${currentPage - 1}">‹
                                                Prev</a>
                                        </c:if>
            
                                        <!-- Các trang trong nhóm -->
                                        <%
                                            int notCurrentPageCnt = -1;
                                            for (int i = groupStart; i <= groupEnd; i++) {
                                                notCurrentPageCnt++;
                                        %>
                                            <a href="admin?action=accountList&page=<%=i%>"
                                            class="<%= (i == currentPage) ? "current" : "notCurrentPage" + notCurrentPageCnt %>">
                                                <%=i%>
                                            </a>
                                        <%
                                            }
                                        %>
            
                                        <!-- Nút Next -->
                                        <c:if test="${currentPage < totalPages}">
                                            <a class="nextPageBtn"
                                                href="admin?action=accountList&page=${currentPage + 1}">Next
                                                ›</a>
                                        </c:if>
    
                                        <!-- Nút tới trang cuối -->
                                        <c:if test="${currentPage < totalPages}">
                                            <a class="lastPageBtn" href="admin?action=accountList&page=${totalPages}">Last
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
    <!-- Delete Account Modal -->
    <div class="modal fade" id="deleteAccountModal" tabindex="-1" aria-labelledby="deleteAccountModalLabel"
        aria-hidden="true" style="position: fixed; top: 10%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">Xóa tài khoản</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <c:if test="${not empty message}">
                        <div class="alert-error">
                            &#9888; ${message}
                        </div>
                    </c:if>
                    <p>Bạn có chắc chắn muốn xóa tài khoản <strong>
                            <c:out value="${accountID}" />
                        </strong> không?</p>
                    <p class="text-danger">Hành động này không thể hoàn tác!</p>
                </div>
                <div class="modal-footer">
                    <form action="admin" method="post">
                        <input type="hidden" name="action" value="deleteAccount" />
                        <input type="hidden" name="accountID" value="${accountID}" />
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-danger">Xóa</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
        window.addEventListener('DOMContentLoaded', (event) => {
            var myModal = new bootstrap.Modal(document.getElementById('deleteAccountModal'));
            myModal.show();
        });
    </script>
    <div style="cursor: pointer;" id="overlay" class="filter-overlay"></div>
    <div id="filterModal" class="filter-modal">
        <h3>Bộ lọc tài khoản</h3>
        <form action="admin" method="get">
            <input type="hidden" name="action" value="accountFilter" />
            <div class="mb-3">
                <label for="role" class="form-label">Quyền đăng nhập</label>
                <select class="form-select" id="role" name="role">
                    <option value="">Chọn quyền đăng nhập</option>
                    <option value="student">Student</option>
                    <option value="admin">Admin</option>
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
            checkboxes = document.getElementsByName('accountIds');
            for (var i = 0; i < checkboxes.length; i++) {
                checkboxes[i].checked = source.checked;
            }
        }
    </script>
        <div class="modal fade" id="deleteAccountsModal" tabindex="-1" aria-labelledby="deleteAccountsModalLabel"
        aria-hidden="true" style="position: fixed; top: 10%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">Xóa tài khoản</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" style="display: flex; flex-direction: column;">
                    <p>Nhập lại mã sau để xác nhận xóa:
                        <strong id="randomCode"></strong>
                    </p>
                    <input style="border-radius: 5px; border: 1px solid rgba(0, 0, 0, 0.3); margin-bottom: 15px; outline: none; padding: 5px 0px 5px 10px;" type="text" id="deleteCodeConfirmInput" placeholder="Nhập mã xác nhận" required />
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
            var myModal = new bootstrap.Modal(document.getElementById('deleteAccountsModal'));
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