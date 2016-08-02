#Login Social do Banco do Brasil

O login social do Banco do Brasil permite que aplicativos de parceiros façam transações com o Banco do Brasil.
Antes de utilizar esse SDK é importante que você faça o cadastro do seu aplicativo parceria junto ao Banco do Brasil.
O processo de cadastro pode ser feito em XXXXXXXX

Para começar adicione a seguinte dependência ao seu projeto:


```
dependencies {
  …
  compile 'br.com.bb.loginbb:bank-login:0.0.1'
  …
}
```

Lembrando que necessário que você utilize o seguinte repositório de pacotes no seu projeto.

```
allprojects {
  repositories {
        …
        maven {
          url 'https://dl.bintray.com/oximer/maven/'
        }
	      …
	}
}
```

Após adicionar tal dependência, vamos entender como funciona o SDK. 
O fluxo é baseado em OAuth2.

##Recebendo credenciais de instalação

Ao cadastrar seu aplicativo no Banco do Brasil, você receberá um PartnerId e um PartnerSecret em nosso portal web. 
Essas credenciais serão utilizadas para que você gere credenciais de instalação para seu usuário.

Assim cada instalação do seu aplicativo terá um InstallationID e InstallationSecret. 
Existe uma relação parentesco entre as credenciais de instalação que você gerar e sua credencial de parceiro.

- PartnerID/PartnerSecret 1
  - InstallationID/InstallationSecret 1
  - InstallationID/InstallationSecret 2

Você deve utilizar nossa API XXXX para geração de credenciais.

### Exemplo de geração de InstallationID/InstallationSecret.



## Inicilizando seu SDK

Existem várias maneiras para inicializar o SDK, uma alternativa interessante é criar uma classe que estenda sua aplicação. 

```
BBLogin.initialize(getApplicationContext());
```

Continue a inicialização definindo algumas informações básicas importantes para o funcionamento do SDK.

```
BBLogin.setPushCode("987654321");
BBLogin.setRedirectUrl("loginbb://auth");
BBLogin.setInstallationCredentials(“InstallationId”,”InstallationSecret”)
```

Caso, seu aplicativo esteja em fase de desenvolvimento e você ainda não possua backend funcional, você pode definir o seu PARTNER_ID and PARTNER_SECRET no SDK.
**Entretanto, você não deve publicar seu aplicativo dessa maneira. Isso acabaria por expor suas credenciais, permitindo que outras pessoas gerem credenciais de instalação vinculados ao seu aplicativo.**

```
BBLogin.setPartnerId("7da5626c-050a-4c14-874d-6c188f8abdf");
BBLogin.setPartnerSecret("7b244153-35ea-4d79-810c-ebd8cc921573"); 
```


### Adicione o Botão de login

Para adicionar um botão de login é bastante simples.

```
<br.com.bb.loginbb.ui.BBButton
android:id="@+id/bbbutton"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/login_button"
custom:scope="consulta"/>
```

Você pode definir o atribute **scope** conforme a necessidade de sua aplicação.


### Definindo o BBLoginCallbackManager

Após adicionar um botão de login na tela, você deve definir o BBLoginCallbackManager da sua aplicação.

```
mCallbackManager = new BBLoginCallbackManager();
mCallbackManager.registerCallback(new BBLoginCallback() {
    @Override
    public void onSuccess(AccessToken accessToken) {
    //On Success.
    }
    @Override     
    public void onCancel() {
    //Login cancelled by the user.
    }
    @Override
    public void onError(BBLoginException exception) {
    //Error during the login.
    }
});

mButton.setLoginCallbackManager(mCallbackManager);
```

#### Access token

Em caso de sucesso você receberá access token seguindo padrão do OAuth 2.0
O Access tem validade e você deve checar se ele está expirado antes de utilizá-lo.

#### Refresh Token (Atualizando seu token expirando)

Caso seu token esteja expirado, você pode usar a classe RefreshAccessTokenRequest para atualizar seu access token.
Caso seu refresh token também esteja expirado será necessário proceder o processo de autorização novamente (login).


Explore o example existente nesse projeto para entender um pouco funcionamento do SDK. 


### Aplicação de Teste

```
PushCode = 12345678
RedirectUrl = loginbb://auth
PartnerId = 7da5626c-050a-4c14-874d-6c188f8abdf
PartnerSecret = 7b244153-35ea-4d79-810c-ebd8cc921573
```
 
