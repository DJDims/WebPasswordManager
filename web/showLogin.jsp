
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="my-5 w-100 d-flex justify-content-center">Введите логин и пароль</h2>
<div class="w-100 d-flex justify-content-center">
    <form action="login" method="POST" >
      <div class="card border-0 mb-3" style="width: 20em;">
        <div class="form-group">
            <label for="login" class="form-label mt-4">Логин</label>
            <input type="text" class="form-control" id="login" name="login" value="${name}">
        </div>
        <div class="form-group">
            <label for="password" class="form-label mt-4">Пароль</label>
            <input type="password" class="form-control" id="password" name="password" value="${name}">
        </div>
        <input class="btn btn-primary mt-5" type="submit" value="Войти">
        <h4 class="w-100 text-center my-3"><a href="showRegistration">Зарегистрироваться</a></h4>
      </div>
    </form>
</div>