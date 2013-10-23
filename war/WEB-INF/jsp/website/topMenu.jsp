
	<c:forEach items="${languageRootCategories}" var="languageRootCategory" varStatus="varStatus">	
		<li><a href="${languageRootCategory.url}">${languageRootCategory.name}</a>
			<ul>
				<li class="menu_drop_top"></li>
    			<c:forEach items="${languageRootCategory.subCategories}" var="category">
				<li><a href="${category.url}">${category.name}</a></li>
				</c:forEach>
			</ul>
		</li>
		<li><img src="image/website/menu_pipe.png" alt="" /></li>
	</c:forEach>

