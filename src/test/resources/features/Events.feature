Feature: Validación de eventos
  Scenario: Verificar un evento exitoso
    Given que exista algún  evento configurado
    When se envíe el evento
    Then el webhook deberá enviar una respuesta 200

    Scenario: Validar signature del evento
      Given que exista algún  evento configurado
      When se envíe el evento con firma válida
      Then el webhook deberá enviar una respuesta 200
      
    Scenario: Validación de signature errado
      Given que exista algún  evento configurado
      When se envíe el evento con firma inválida
        """
      {
        "event": "transaction.updated",
        "data": {
          "transaction": {
            "id": "123-456",
            "status": "APPROVED",
            "amount_in_cents": 150000
          }
        },
        "environment": "test",
        "signature": {
          "properties": ["id","status","amount_in_cents"],
          "checksum": "checksum_incorrecto"
        },
        "timestamp": 1530291411
      }
      """
      Then el webhook deberá enviar una respuesta 400