
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="my-5 w-100 d-flex justify-content-center">Регистрация нового пользователя</h2>
<div class="w-100 d-flex justify-content-center">
    <form action="registration" method="POST">
        <div class="card border-0 mb-3" style="width: 70em;">
            <div class="row gx-5">
                <div class="form-group col">
                    <label class="form-label mt-4">Имя</label>
                    <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Введите имя">
                </div>
                <div class="form-group col">
                    <label class="form-label mt-4">Фамилия</label>
                    <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Введите фамилию">
                </div>
                <div class="form-group col">
                    <label class="form-label mt-4">Телефон</label>
                    <input type="text" class="form-control" id="tel" name="tel" placeholder="Введите телефон">
                </div>
            </div>
            <div class="row gx-5">
                <div class="form-group col">
                    <label class="form-label mt-4">Логин</label>
                    <input type="text" class="form-control" id="login" name="login" placeholder="Введите логин">
                </div>
                <div class="form-group col">
                    <label class="form-label mt-4">Пароль</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль">
                </div>
                <div class="form-group col">
                    <label class="form-label mt-4">Подтверждение пароля</label>
                    <input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm" placeholder="Повторите пароль">
                </div>
            </div>
        </div>
        <div>
            <div class="w-100 d-flex justify-content-center">
                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </div>
        </div>
    </form>
</div>