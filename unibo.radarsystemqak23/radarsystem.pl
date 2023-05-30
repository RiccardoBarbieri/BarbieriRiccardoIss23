%====================================================================================
% radarsystem description   
%====================================================================================
context(ctx1, "localhost",  "TCP", "8045").
 qactor( radar, ctx1, "it.unibo.radar.Radar").
  qactor( led, ctx1, "it.unibo.led.Led").
  qactor( sonar, ctx1, "it.unibo.sonar.Sonar").
  qactor( controller, ctx1, "it.unibo.controller.Controller").
