(() => {
	$(document).ready(function () {
		$('#viruses-button').click(() => {
			fetch('/viruses')
				.then((response) => response.json())
				.then((json) => {
					
					$('#choice-title').text('All Viruses');
					$('.container #data-container').remove();
					$('.container').append('<div id="data-container" class="mt-5"></div>');
					
					let table =
						`<table class="table">` +
						`<thead>` +
						`<tr class="text-center">` +
						`<th scope="col">#</th>` +
						`<th scope="col">Name</th>` +
						`<th scope="col">Magnitude</th>` +
						`<th scope="col">Released On</th>` +
						`<th scope="col">Actions</th>` +
						`</tr>` +
						`</thead>` +
						`<tbody>`;
					
					json.forEach((x, y) => {
						
						table +=
							`<tr class="text-center">` +
							`<th scope="row">${y + 1}</th>` +
							`<td>${x.name}</td>` +
							`<td>${x.magnitude}</td>` +
							`<td>${x.releasedOn}</td>` +
							`<td class="d-flex justify-content-between">` +
							`<a sec:authorize="hasAnyAuthority('ADMIN', 'MODERATOR')" class="btn btn-outline-secondary" href="/viruses/edit?id=${x.id}">Edit</a>` +
							`<a sec:authorize="hasAnyAuthority('ADMIN', 'MODERATOR')" class="btn btn-outline-secondary" href="/viruses/delete?id=${x.id}">Delete</a>` +
							`</td>` +
							`</tr>`;
					});
					
					table +=
						`</tbody>` +
						`</table>`;
					
					$('#data-container').append(table);
				});
		});
		
		$('#cities-button').click(() => {
			fetch('/cities')
				.then((response) => response.json())
				.then((json) => {
					
					$('#choice-title').text('All Capitals');
					$('.container #data-container').remove();
					$('.container').append('<div id="data-container" class="mt-5"></div>');
					
					let table =
						`<table class="table">` +
						`<thead>` +
						`<tr class="text-center">` +
						`<th scope="col">#</th>` +
						`<th scope="col">Name</th>` +
						`<th scope="col">Latitude</th>` +
						`<th scope="col">Longitude</th>` +
						`</tr>` +
						`</thead>` +
						`<tbody>`;
					
					json.forEach((x, y) => {
						
						table +=
							`<tr class="text-center">` +
							`<th scope="row">${y + 1}</th>` +
							`<td>${x.name}</td>` +
							`<td>${x.latitude}</td>` +
							`<td>${x.longitude}</td>` +
							`</tr>`;
					});
					
					table +=
						`</tbody>` +
						`</table>`;
					
					$('#data-container').append(table);
				});
		});
	})
})();