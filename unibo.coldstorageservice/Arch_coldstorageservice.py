from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
#os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('coldstorageserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxcoldstorage', graph_attr=nodeattr):
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
          servicestatusgui=Custom('servicestatusgui','./qakicons/symActorSmall.png')
          appl=Custom('appl','./qakicons/symActorSmall.png')
          ticketmanager=Custom('ticketmanager','./qakicons/symActorSmall.png')
          alarmsonar=Custom('alarmsonar','./qakicons/symActorSmall.png')
          ledmanager=Custom('ledmanager','./qakicons/symActorSmall.png')
          coldroommanager=Custom('coldroommanager','./qakicons/symActorSmall.png')
          transporttrolleymanager=Custom('transporttrolleymanager','./qakicons/symActorSmall.png')
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
          sonar=Custom('sonar(coded)','./qakicons/codedQActor.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
diag
