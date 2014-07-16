
	<c:forEach items="${languageRootCategories}" var="languageRootCategory" varStatus="varStatus">	
		<li class="drop-link"><a href="${languageRootCategory.url}">${languageRootCategory.name}</a><span></span>
			<ul class="dd">
    			<c:forEach items="${languageRootCategory.subCategories}" var="category">
				<li><a href="${category.url}">${category.name}</a></li>
				</c:forEach>
			</ul>
		</li>
	</c:forEach>

