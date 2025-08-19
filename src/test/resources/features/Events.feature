Feature: Validación de eventos
  Scenario: Verificar un evento exitoso
    Given que exista algún  evento configurado
    When se envíe el evento
      """
      {
        "event": "transaction.approved",
        "data": {
          "transaction": {
            "id": "123",
            "status": "APPROVED"
          }
        }
      }
      """
    Then el webhook deberá enviar una resuesta 200