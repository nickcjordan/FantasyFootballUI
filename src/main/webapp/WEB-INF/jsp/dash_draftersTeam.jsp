<tr class="thin-row">
	<td class="id-db_drafterTeam">${player.id}</td>
	<td class="name-db_drafterTeam"><a class="nameLink" data-toggle="modal" data-target="#${player.id}playerModal">${player.getNameAndTags()}</a></td>
	<td class="team-db_drafterTeam">${player.teamName}</td>
	<td class="bye-db_drafterTeam">${player.bye}</td>
</tr>

<%@include file="modal.jsp"%>