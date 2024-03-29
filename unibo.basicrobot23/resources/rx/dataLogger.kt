package rx

 

import alice.tuprolog.Struct
import alice.tuprolog.Term
import java.io.PrintWriter
import java.io.FileWriter
import it.unibo.kactor.ActorBasic
import unibo.basicomm23.interfaces.IApplMessage


class dataLogger(name : String) : ActorBasic(name){
	var pw : PrintWriter
	
 	init{
		pw = PrintWriter( FileWriter(name+".txt") )
 	}
    


	override suspend fun actorBody(msg: IApplMessage) {
 		if( msg.msgId() != "sonarRobot" ) return //AVOID to handle other events
 		elabData( msg )
		emitLocalStreamEvent(msg)	//propagate ... 
	}
 
 	protected suspend fun elabData( msg: IApplMessage ){
 		val data  = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0).toString()
		println("	-------------------------------------------- $name data=$data")
   		pw.append( "$data\n " )
		pw.flush()
     }

}