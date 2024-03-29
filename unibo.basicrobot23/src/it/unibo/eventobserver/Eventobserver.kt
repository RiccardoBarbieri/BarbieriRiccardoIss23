/* Generated by AN DISI Unibo */ 
package it.unibo.eventobserver

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Eventobserver ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t03",targetState="handleEvent",cond=whenEvent("sonardata"))
					transition(edgeName="t04",targetState="handleEvent",cond=whenEvent("obstacle"))
				}	 
				state("handleEvent") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						 CommUtils.outcyan("handleEvent $currentMsg")  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t05",targetState="handleEvent",cond=whenEvent("sonardata"))
					transition(edgeName="t06",targetState="handleEvent",cond=whenEvent("obstacle"))
				}	 
			}
		}
}
