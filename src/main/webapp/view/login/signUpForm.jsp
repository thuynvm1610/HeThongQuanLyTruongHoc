<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hệ thống quản lý giáo dục</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <style>
        .auth-container {
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: rgba(255, 255, 255, 0.3);
        }

        .auth-card {
            width: 100%;
            max-width: 400px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .auth-header {
            background-color: #3498db;
            color: white;
            padding: 20px;
            text-align: center;
        }

        .auth-body {
            padding: 30px;
            background-color: white;
        }

        .auth-footer {
            text-align: center;
            padding: 15px;
            background-color: #f8f9fa;
            border-top: 1px solid #eee;
        }

        .btn-auth {
            background-color: #3498db;
            border: none;
            padding: 10px;
            font-weight: 500;
        }

        .btn-auth:hover {
            background-color: #2980b9;
        }

        .auth-link {
            color: #3498db;
            text-decoration: none;
        }

        .auth-footer__button:hover {
            text-decoration: underline;
        }

        .bg__img {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-position: center;
            background-size: cover;
            z-index: -1;
        }
    </style>
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
    <!-- Register Page -->
    <div class="bg__img" style="background-image: url('https://www.haui.edu.vn/media/105/t105218.jpg');"></div>
    <div class="auth-container" id="registerPage">
        <div class="auth-card">
            <div class="auth-header">
                <h4><i class="fas fa-user-plus me-2"></i>Đăng ký tài khoản</h4>
            </div>
            <div class="auth-body">
                <form method="post" action="login">
                    <input type="hidden" name="action" value="addStudentAccount">
                    <div class="mb-3">
                        <label for="studentID" class="form-label">Nhập mã sinh viên</label>
                        <input type="text" class="form-control" id="studentID" name="studentID" value="${studentID}"
                            style="box-shadow: none;" required>
                    </div>
                    <div class="mb-3">
                        <label for="username" class="form-label">Tên tài khoản</label>
                        <input type="text" class="form-control" id="username" name="username" value="${username}"
                            style="box-shadow: none;" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Mật khẩu</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="password" name="password"
                                value="${password}" style="box-shadow: none;" required>
                            <button class="btn btn-outline-secondary" type="button" id="toggleRegisterPassword">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                            style="box-shadow: none;" required>
                    </div>
                    <button type="submit" class="btn btn-auth btn-primary w-100">Đăng ký</button>
                </form>
            </div>
            <div class="auth-footer">
                <form method="get" action="login">
                    <input type="hidden" name="action" value="loginForm">
                    Bạn đã có tài khoản?
                    <button class="auth-footer__button" type="submit"
                        style="background: none; border: none; color: #3498db;">
                        Đăng nhập ngay
                    </button>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            function setupPasswordToggle(buttonId, inputId) {
                const toggleBtn = document.getElementById(buttonId);
                const passwordInput = document.getElementById(inputId);

                toggleBtn.addEventListener('click', function () {
                    const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
                    passwordInput.setAttribute('type', type);
                    toggleBtn.innerHTML = type === 'password' ? '<i class="fas fa-eye"></i>' : '<i class="fas fa-eye-slash"></i>';
                });
            }

            setupPasswordToggle('toggleRegisterPassword', 'password');
        });
    </script>
</body>

</html>