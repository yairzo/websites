/*
 * jQuery RTE plugin 0.2 - create a rich text form for Mozilla, Opera, and Internet Explorer
 *
 * Copyright (c) 2007 Batiste Bieler
 * Distributed under the GPL (GPL-LICENSE.txt) licenses.
 */

// define the rte light plugin
jQuery.fn.rte = function(css_url, iframe, formAction) {
    if(document.designMode || document.contentEditable)
    {
        $(this).each( function(){
            var textarea = $(this);
            //enableDesignMode(textarea, css_url);
        });
    }
}


/*function setSelectedType(node, select) {
        while(node.parentNode) {
            var nName = node.nodeName.toLowerCase();
            for(var i=0;i<select.options.length;i++) {
                if(nName==select.options[i].value){
                    select.selectedIndex=i;
                    return true;
                }
            }
            node = node.parentNode;
        }
        select.selectedIndex=0;
        return true;
    }
*/
    function getSelectionElement(iframe) {
        if (iframe.contentWindow.document.selection) {
            // IE selections
            selection = iframe.contentWindow.document.selection;
            range = selection.createRange();
            try {
                node = range.parentElement();
            }
            catch (e) {
                return false;
            }
        } else {
            // Mozilla selections
            try {
                selection = iframe.contentWindow.getSelection();
                range = selection.getRangeAt(0);
            }
            catch(e){
                return false;
            }
            node = range.commonAncestorContainer;
        }
        return node;
    }

    function getSelectionRange(iframe) {
        var range;
        if (iframe.contentWindow.document.selection) {
            // IE selections
            selection = iframe.contentWindow.document.selection;
            range = selection.createRange();

        } else {
            // Mozilla selections
            try {
                selection = iframe.contentWindow.getSelection();
                range = selection.getRangeAt(0);
            }
            catch(e){
                return false;
            }

        }
        return range;
    }



	function toolbar(iframe, css_url, formAction) {
        tb = $("<div class='rte-toolbar'><div><table dir='ltr' width='100%'><tr><td align='left'>\
			<p>\
                <a href='#' class='subtitle1'><img src='image/icon_h2.gif' title='subtitle 1' /></a>\
				<a href='#' class='subtitle2'><img src='image/icon_h3.gif' title='subtitle 2' /></a>\
				<a href='#' class='subtitle3'><img src='image/icon_h4.gif' title='subtitle 3' /></a>\
				<a href='#' class='paragraph'><img src='image/icon_paragraph.gif' title='paragraph' /></a>\
                <a href='#' class='bold'><img src='image/icon_bold.gif' title='bold' /></a>\
                <a href='#' class='italic'><img src='image/icon_italic.gif' title='italic' /></a>\
                <a href='#' class='unorderedlist'><img src='image/icon_list.gif' title='unordered list' /></a>\
                <a href='#' class='orderedlist'><img src='image/icon_list_ordered.gif' title='ordered list' /></a>\
                <a href='#' class='link'><img src='image/icon_link.gif' title='link' /></a><br/>\
                <a href='#' class='alignLeft'><img src='image/icon_left.gif' title='align left' /></a>\
                <a href='#' class='alignRight'><img src='image/icon_right.gif' title='align right' /></a>\
                <a href='#' class='view'><img src='image/icon_view.gif' title='show results' /></a>\
                <a href='#' class='html'><img src='image/icon_html.gif' title='show html' /></a>\
                <a href='#' class='save'><img src='image/icon_save.gif' title='save' /></a>\
                <a href='#' class='cleanMSWordCode'><img src='image/icon_word.gif' title='Clean text pasted from MSWord' /></a>\
                <a href='#' class='removeformat'><img src='image/icon_save.gif' title='Remove format' /></a>\
                <a href='#' class='undo'><img src='image/icon_undo.gif' title='Undo' /></a>\
                <a href='#' class='redo'><img src='image/icon_redo.gif' title='Redo' /></a>\
            </p></div></div></td></tr></table>");

        $('.subtitle1', tb).click(function(){ formatText(iframe, "formatblock", '<h2>'); return false;});
        $('.subtitle2', tb).click(function(){ formatText(iframe, "formatblock", '<h3>'); return false;});
        $('.subtitle3', tb).click(function(){ formatText(iframe, "formatblock", '<h4>'); return false;});
        $('.paragraph', tb).click(function(){ formatText(iframe, "formatblock", '<p>'); return false;});
        $('.bold', tb).click(function(){ formatText(iframe, 'bold');return false; });
        $('.italic', tb).click(function(){ formatText(iframe, 'italic');return false; });
        $('.unorderedlist', tb).click(function(){ formatText(iframe, 'insertunorderedlist');return false; });
        $('.orderedlist', tb).click(function(){ formatText(iframe, 'insertorderedlist');return false; });
        $('.link', tb).click(function(){
            var p=prompt("URL:");
            if(p)
                formatText(iframe, 'insertHTML', '<a href=\"'+ p + '\" target=\"_new\">' +getSelectionRange(iframe) +'</a>');
            return false; });
        $('.image', tb).click(function(){
           var newWin = window.open('uploadImage.htm','','width=600, height=300, location=no, status=no, scrollbars=yes' );
            return false;});
         $('.file', tb).click(function(){
           var newWin = window.open('uploadFile.htm','','width=600, height=300, location=no, status=no, scrollbars=yes' );
            return false;});
        $('.table', tb ).click(function(){formatText(iframe, 'insertHTML', '<table><tr><td>&nbsp;</td><td>&nbsp;</td</tr></table>');return false;});
        $('.alignLeft', tb ).click(function(){formatText(iframe, 'justifyleft'); return false;});

		$('.alignRight', tb ).click(function(){formatText(iframe, 'justifyright'); return false;});

       $('.view', tb).click(function(){
			$('#form').append("<input id=\"action\" type=\"hidden\" name=\"action\" value=\"ajaxSave\">");
			disableDesignMode(iframe, false, css_url, false, formAction);
        	tb.remove();
        	$('div.actions').hide();
			var content = $('textarea#body').val();
			$('div.bodyTA').hide();
			$('div.body').empty().append(content).show();
        	return false;
        });
        $('.html', tb).click(function(){
			disableDesignMode(iframe, false, css_url, false, formAction);
			tb.remove();
			$('div.actions').show();
        	return false;});
        $('.save', tb).click(function(){
	        $('#form').append("<input id=\"action\" type=\"hidden\" name=\"action\" value=\"ajaxSave\">");
			disableDesignMode(iframe, false, css_url, true, formAction);
        	return false;});
        $('.cleanMSWordCode', tb).click(function(){
        	disableDesignMode(iframe, false, css_url, true, formAction);
        	location.reload(true);
        	return false;
			});

		$('.removeformat', tb ).click(function(){
			re=/<\/?\w+[^>]*>/;
			var html = ""+getSelectionRange(iframe);
			var unformattedText = html.replace(re,"");
			formatText(iframe, 'insertHTML', unformattedText);
			 return false;});

	    $('.undo', tb).click(function(){ formatText(iframe, "undo"); return false;});
	    $('.redo', tb).click(function(){ formatText(iframe, "redo"); return false;});


        $(iframe).parents('form').submit(function(){
            $('input#action').remove();
            disableDesignMode(iframe, true, css_url, false, formAction);
         });
        var iframeDoc = $(iframe.contentWindow.document);

        var select = $('select', tb)[0];
        //iframeDoc.mouseup(function(){ setSelectedType(getSelectionElement(iframe), select);return true;});
        //iframeDoc.keyup(function(){ setSelectedType(getSelectionElement(iframe), select);return true;});

        return tb;
    }

    function formatText(iframe, command, option) {
        iframe.contentWindow.focus();
        try{
            iframe.contentWindow.document.execCommand(command, false, option);
        }catch(e){console.log(e)}
        iframe.contentWindow.focus();
    }




    function tryEnableDesignMode(iframe, doc, callback) {
        try {
            iframe.contentWindow.document.open();
            iframe.contentWindow.document.write(doc);
            iframe.contentWindow.document.close();
        } catch(error) {
            console.log(error)
        }
        if (document.contentEditable) {
            iframe.contentWindow.document.designMode = "on";
            callback();
            return true;
        }
        else if (document.designMode != null) {
            try {
                iframe.contentWindow.document.designMode = "on";
                callback();
                return true;
            } catch (error) {
                console.log(error)
            }
        }
        setTimeout(function(){tryEnableDesignMode(iframe, doc, callback)}, 250);
        return false;
    }

    function local_alert(){
	    alert("calling from main page");
    };


    function enableDesignMode(textarea, css_url, isSave, formAction) {
        // need to be created this way
        //var iframe = document.createElement("iframe");

        if (editorOpen == false){
        editorOpen = true;


        iframe.frameBorder=0;
        iframe.frameMargin=0;
        iframe.framePadding=0;

        if(textarea.attr('class'))
            iframe.className = textarea.attr('class');
        if(textarea.attr('id'))
            iframe.id = textarea.attr('id');
        if(textarea.attr('name'))
            iframe.title = textarea.attr('name');
        textarea.after(iframe);
        var css = "";
        if(css_url)
            var css = "<link type='text/css' rel='stylesheet' href='"+css_url+"' />"
        var content = textarea.val();
        // Mozilla need this to display caret

        if($.trim(content)=='')
            content = '<br>';
        var doc = "<html><head>"+css+"</head><body class='frameBody'>"+content+"</body></html>";
        tryEnableDesignMode(iframe, doc, function() {
            $(iframe).attr('width', 100);
            if (isSave==false) {
            	$(iframe).before(toolbar(iframe, css_url, formAction));
            }
            textarea.remove();
        });
        }


    }

    function disableDesignMode(iframe, submit, css_url, isSave, formAction) {
		if (editorOpen == true){

        var content = iframe.contentWindow.document.getElementsByTagName("body")[0].innerHTML;
        if(submit==true)
            var textarea = $('<input type="hidden"/>');
        else
            var textarea = $('<textarea></textarea>');
        textarea.val(content);
        t = textarea.get(0);
        if(iframe.className)
            t.className = iframe.className;
        if(iframe.id)
            t.id = iframe.id;
        if(iframe.title)
            t.name = iframe.title;
        $(iframe).before(textarea);
        if(submit!=true)
            $(iframe).remove();
        editorOpen = false;

        var options = {
       		url:       formAction ,        // override for form's 'action' attribute
        	type:      'POST'
     	};
	    $('#form').ajaxSubmit(options);
	    if (isSave==true) {
	    	enableDesignMode(textarea, css_url, true, formAction);
	    }

        }


    }

