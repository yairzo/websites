/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

//CKEDITOR.config.language = 'en'; 

CKEDITOR.editorConfig = function( config ) {

    /* config.pbckcode  = {
    		'cls'         : 'prettyprint linenums',	// the class(es) added to the pre tag, useful if you use a syntax highlighter (here it is Google Prettify)
    		'modes'       : [ 
    			['HTML' , 'html'] ], // all the languages you want to deal with in the plugin
    	  	'defaultMode' : 'html', // the default value for the mode select. Well in fact it is the first value of the mode array
    		'theme' : 'textmate' // the theme of the code editor
    	};	*/
	config.removeButtons = 'NewPage,Templates,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,'
		+'Flash,Smiley,PageBreak,SpecialChar,Blockquote,Underline,Subscript,Superscript,ShowBlocks,Styles';

	config.format_tags = 'p;h1;h2;h3;pre';
	
	config.enterMode = CKEDITOR.ENTER_BR;	
	
	config.removePlugins = 'tableresize';

	config.extraPlugins = 'mediaembed'//,slideshow';
	
	config.scayt_autoStartup = true;
	
	config.floatSpaceDockedOffsetX = 170;
	
	config.fillEmptyBlocks = false;
	
	config.filebrowserImageBrowseUrl = '/uploadImage.html';
	
	config.allowedContent = true;
	
	config.startupShowBorders = false;
};


