# RadioPatioAndrois

1. > Instrucciones sobre cómo instalar esta app
    - Hacer un git clone al siguiente repositorio de github: https://github.com/andogoiko/RadioPatioAndrois.git
    - Crear un device nuevo en Android Studio que lleve instalado Google play, ya que es necesario tener spotify instalado para poder usar su servicio de api
    - (Esto te lo puedes saltar porque te doy mi cuenta) Hay que tener en cuenta que si no eres premium habrá funcionalidades que no se desempeñen correctamente (Julen te mandaré por correo mi cuenta para que puedas acceder ya que es premium)
    - Una vez todo esto esté preparado, hay que añadir el usuario y el fingerprint en el dashboard de spotify (https://developer.spotify.com/dashboard/), debes de ser el creador de la app, así que solo funciona con mi cuenta. Por ello al loguearse hay que acceder a la app correspondiente (Radio Patio en este caso)
    - ![dashboard_inicio](/images/dashboard_inicio.png)
    - Dentro de la app ir a edit settings
    - ![dashboard_inicio](/images/dashboard_edit_settings.png)
    - En la configuración que se abre hay que dirigirse a "Android Packages" y añadir el fingerprint, junto con el nombre del package
    - ![dashboard_inicio](/images/dashboard_fingerprint.png)
    - Para conseguir el fingreprint has de dirigirte a la raíz de la carpeta clonada es decir a "RadioPatioAndrois" y abrir un git bash
    - ![dashboard_inicio](/images/raiz_bash.png)
    - Y debes de ejecutar el siguiente comando -> keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
    - Te responderá con 2 fingerprints, pero debes de copiar el SHA1
    - ![dashboard_inicio](/images/bash_fingerprint.png)
    - Ahora puedes volver al dashboard, lo añades y guardas y tu dispositivo es apto para usar esta app (en este caso el pc que emula)
    - ![dashboard_inicio](/images/dashboard_save_fingerprint.png)
    - Para añadir el usuario (si tienes mi cuenta de master no te hace falta ya que iniciarás en la app desde el emulador con la cuenta de spotify que te cederé) hay que darle a la opción que tienen al lado los settings. Lo cual abrirá la siguiente lista:
    - ![dashboard_inicio](/images/dashboard_add_user.png)
    - Clicas en "add new user" y mediante el formulario podrás añadir los usuarios que quieras (hay un límite de 25 si no pagas más).
    - ![dashboard_inicio](/images/dashboard_add_user_form.png)
    - Con esto ya puedes disfrutar de esta app que espero ir mejorando y añadiendo funcionalidades poco a poco.

2. > Funcionalidades
    - LaunchActivity que loguea automáticamente y autoriza al usuario para utilizar los servicios de Spotify
    - MainActivity que contiene un player estático y 3 diferentes fragmentos, uno con tus últimos temas escuchados, otro que contiene un buscador y el tercero aún no implementado que tendrá tus distintas playlists
    - Cada uno de estos hace uso de los objetos de la librería de spotify, cualquier duda puede que se resuelva viendo los comentarios del código.