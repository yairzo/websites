/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.removeButtons = 'NewPage,Templates,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,'
		+'Anchor,Flash,Smiley,PageBreak,Iframe,SpecialChar,Underline,Subscript,Superscript,ShowBlocks,Styles';

	config.format_tags = 'p;h1;h2;h3;pre';
	
	config.enterMode = CKEDITOR.ENTER_BR;	

};
