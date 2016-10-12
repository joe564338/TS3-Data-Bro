# TS3-Data-Bro

Documentation...
Harxer2DTemplate
================

	main.engine.HXStartup: Where the main method resides 
• Static reference to singleton HXMasterWindow and instantiates
_________________
	ui.HXMasterWindow: The applications primary UI window
• Contains the panel for 2D world rendering subclassed as HXViewPanel
• Adjustable constants for application window’s dimensions plus child HXViewPanel dimensions.
• References input.HXKey file for keyboard button presses, initialized in this class
_________________
	input.HXKey: Stores static fields for all keyboard keys used in the application
• Add to the static HashMap to listen for more keyboard presses
__________________
	ui.HXViewPanel: Owns a panel where the 2D world’s entities are drawn subclassed as HXWorldPanel. Also handles the panning of that world with the  selected keys
• Nothing in this class needs adjusting for forked projects
__________________
	ui.HXWorldPanel: A panel that renders all of its HXWorld’s entities array
• Nothing in this class needs adjusting for forked projects
• Owns a HXClock
• Implements the HXClockRenderer interface for repaintWorld
__________________
	world.HXClock: Pulses on a fixed timestep loop to its parent panel
• Nothing in this class needs adjusting for forked projects
• Allows world to be paused
• Calls parent panel repaintWorld
• Calls parent panel’s world updateEntities

___________________
	world.HXWorld: Intended to hold properties like friction and gravity. Currently contains an array of all entities as well as the size of the world
• Adjustable constants for the world’s x:y dimensions
