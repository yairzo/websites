<%@ page  pageEncoding="UTF-8" %>

		<form:form id="form" name="form" method="POST" action="uploadImage.html" commandName="command" enctype="multipart/form-data">

		<table width="80%">
				<tr>
      				<td>
      					&nbsp;
      				</td>
      			</tr>
      				<tr>
		<td class="addFile">
			:צרף קובץ
			<input class="green" type="file" name="addfile"  />
		</td>
	</tr>
	<tr>
	<td>
		<input class="green" type="submit" name="submit"  />
		</td>
	</tr>
    </table>
</form:form>