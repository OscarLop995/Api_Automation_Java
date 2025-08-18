Feature: Transacciones
  Scenario: Transacción exitosa
    Given se genere un token de aceptación válido "pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s4mOt7"
    When el usuario envíe la solicitud
    Then se deberá ver una respuesta de que la transacción fue exitosa