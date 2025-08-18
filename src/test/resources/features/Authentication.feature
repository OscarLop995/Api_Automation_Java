Feature: Autenticación de usuario
  Scenario: Llave válida de cliente
    Given se desee consultar la información del cliente con llave "pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s4mOt7"
    When envíe la solicitud
    Then deberá responder 200 y mostrarse la respuesta con la data del cliente

  Scenario: Llave inválida de cliente
    Given se desee consultar la información del cliente con llave "pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s41234"
    When envíe la solicitud
    Then deberá indicar que no existe el cliente

  Scenario: Enviar solicitud sin llave
    Given se desee consultar la información del cliente con llave ""
    When envíe la solicitud
    Then deberá indicar que es requerida la llave
