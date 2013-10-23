 <style type="text/css">

	/* remove the list style */
	#nav {
    	margin:0;
    	padding:0;
    	list-style:none;
	}  
     
    /* make the LI display inline it's position relative so that position absolute can be used in submenu */
    #nav li {
        float:right;
        display:block;
        background:#ccc;
        position:relative;
        z-index:500;
        margin:0 1px;
    }
         
    /* this is the parent menu */
    #nav li a {
        display:block;
        padding:8px 5px 0 5px;
        font-weight:700; 
        height:23px;
        text-decoration:none;
        color:#fff;
        text-align:center;
        color:#333;
    }
 
   	#nav li a:hover {
       	color:#fff;
    }
     
    /* style for selected value */
    #nav a.selected {
       	color:#f00;
    }
    
    /* submenu, it's hidden by default */
    #nav ul {
         position:absolute;
         left:0;
         display:none;
         margin:0 0 0 -1px;
         padding:0;
         list-style:none;
    }
         
    #nav ul li {
        width:100%;
 		list-style-type:none; 
 		white-space: nowrap;
 		border-top:1px solid #fff;
    }
         
    /* display block will make the link fill the whole area of LI */
    #nav ul a {
        display:block; 
        height:15px;
        padding: 8px 5px;
        color:#666;
    }
 
	/* fix ie6 small issue */
	*html #nav ul {
    	margin:0 0 0 -2px;
	}
</style>

<script type="text/javascript">
$(document).ready(function(){
    $("#nav li").hover(
        function(){//onmouseover
        	$(this).children("ul").slideDown();
        },
        function(){//onmouseout
        	$(this).children("ul").slideUp();
        });
});
</script>

  	<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
   			<td align="center">
			<ul id="nav">		
			<c:forEach items="${languageRootCategories}" var="languageRootCategory" varStatus="varStatus">	
           			<li style="width:${780/fn:length(languageRootCategories)}">
           			<a href="${languageRootCategory.url}" class="menu">${languageRootCategory.name}</a>
          	       		<ul>
          	       		<c:forEach items="${languageRootCategory.subCategories}" var="category">
            				<li><a href="${category.url}" class="menu">${category.name}</a></li>
						</c:forEach>
        				</ul>
        			</li>
			</c:forEach>
			</ul>
           	</td>
    </tr>
   	</table>
