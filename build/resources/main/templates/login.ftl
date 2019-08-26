<#import "/templates/base.ftl" as base>
<@base.pagina>
<div class="col-lg-8 col-md-10 col-sm-12 mx-auto">
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-10 mt-2 bg-light px-4 rounded-0 login">
            <div class="row">
                <form class="col-11 py-5" method="post" action="/login">
                    <div class="panel px-2 py-3 bg-white">
                        <label for="user"><strong>Usuario</strong> </label>
                        <input type="text" class="form-control rounded-0" name="username" placeholder="Nombre de Usuario"
                               required=""
                               autofocus=""/>
                        <br>
                        <label for="password"><strong>Contrasena</strong></label>
                        <input type="password" class="form-control rounded-0" name="password" placeholder="Contrasena"
                               required=""/>
                        <br>
                        <hr noshade>
                        <input class="form-check-input" type="radio" name="guardarSesion">
                        <label class="form-check-label" for="guardarSesion">
                            <strong>Recordarme</strong>
                        </label>
                    </div>
                    <button class="btn btn-outline-primary btn-block my-3" type="submit">
                        ENTRAR
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
</@base.pagina>