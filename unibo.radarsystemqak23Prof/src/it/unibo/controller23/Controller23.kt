/* Generated by AN DISI Unibo */ 
package it.unibo.controller23

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Controller23 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 val DLIMIT = 30  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("${name} STARTS - Activates the sonar")
						forward("sonaractivate", "info(ok)" ,"sonar23" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t06",targetState="doBusinessWork",cond=whenEvent("sonardata"))
				}	 
				state("doBusinessWork") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("distance(V)"), Term.createTerm("distance(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var D = payloadArg(0).toInt()  
								forward("polar", "polar($D,90)" ,"radar23" ) 
								if(  D <= DLIMIT  
								 ){forward("ledCmd", "ledCmd(on)" ,"led23" ) 
								}
								else
								 {forward("ledCmd", "ledCmd(off)" ,"led23" ) 
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t07",targetState="doBusinessWork",cond=whenEvent("sonardata"))
				}	 
			}
		}
}
