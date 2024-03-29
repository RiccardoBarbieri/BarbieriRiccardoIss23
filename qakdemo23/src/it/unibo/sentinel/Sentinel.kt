/* Generated by AN DISI Unibo */ 
package it.unibo.sentinel

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sentinel ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 var counter=0  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("sentinel | STARTS")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="watch", cond=doswitchGuarded({ counter==0  
					}) )
					transition( edgeName="goto",targetState="end", cond=doswitchGuarded({! ( counter==0  
					) }) )
				}	 
				state("watch") { //this:State
					action { //it:State
						CommUtils.outblack("sentinel | WATCH")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_watch", 
				 	 					  scope, context!!, "local_tout_sentinel_watch", 60000.toLong() )
					}	 	 
					 transition(edgeName="t00",targetState="timeout",cond=whenTimeout("local_tout_sentinel_watch"))   
					transition(edgeName="t01",targetState="handleAlarm",cond=whenEventGuarded("alarm",{ counter==0  
					}))
				}	 
				state("timeout") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						CommUtils.outblack("sentinel | TIMEOUT")
						 counter++ 	 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						updateResourceRep( "alarm $currentMsg"  
						)
						if( checkMsgContent( Term.createTerm("alarm(V)"), Term.createTerm("alarm(V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outblack("sentinel | ALARM ${payloadArg(0)} ")
						}
						delay(1500) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("explore") { //this:State
					action { //it:State
						CommUtils.outblack("sentinel | exploring (quite fast) ...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="watch", cond=doswitch() )
				}	 
				state("end") { //this:State
					action { //it:State
						CommUtils.outblack("sentinel | ENDS")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
