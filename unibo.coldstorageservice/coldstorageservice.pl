%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxcoldstorage, "localhost",  "TCP", "8021").
 qactor( sonar, ctxcoldstorage, "sonarSimulator").
  qactor( datacleaner, ctxcoldstorage, "rx.dataCleaner").
  qactor( distancefilter, ctxcoldstorage, "rx.distanceFilter").
  qactor( basicrobot, ctxcoldstorage, "external").
  qactor( serviceaccessgui, ctxcoldstorage, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( servicestatusgui, ctxcoldstorage, "it.unibo.servicestatusgui.Servicestatusgui").
  qactor( appl, ctxcoldstorage, "it.unibo.appl.Appl").
  qactor( ticketmanager, ctxcoldstorage, "it.unibo.ticketmanager.Ticketmanager").
  qactor( alarmsonar, ctxcoldstorage, "it.unibo.alarmsonar.Alarmsonar").
  qactor( ledmanager, ctxcoldstorage, "it.unibo.ledmanager.Ledmanager").
  qactor( coldroommanager, ctxcoldstorage, "it.unibo.coldroommanager.Coldroommanager").
  qactor( transporttrolleymanager, ctxcoldstorage, "it.unibo.transporttrolleymanager.Transporttrolleymanager").
