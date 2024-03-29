/* Generated by AN DISI Unibo */ 
package it.unibo.robotposclient

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotposclient ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "ss0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("ss0") { //this:State
					action { //it:State
						request("engage", "engage(worker)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t020",targetState="gototarget",cond=whenReply("engagedone"))
					transition(edgeName="t021",targetState="waitrobotfree",cond=whenReply("engagerefused"))
				}	 
				state("waitrobotfree") { //this:State
					action { //it:State
						CommUtils.outblue("$name | Sorry, the robot is already engaged.")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("gototarget") { //this:State
					action { //it:State
						delay(1000) 
						request("moverobot", "moverobot(2,2)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t022",targetState="handleAnswer",cond=whenReply("moverobotdone"))
					transition(edgeName="t023",targetState="handleAnswer",cond=whenReply("moverobotfailed"))
				}	 
				state("handleAnswer") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						 CommUtils.waitTheUser("$name | hit 1CR")  
						request("moverobot", "moverobot(6,4)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
