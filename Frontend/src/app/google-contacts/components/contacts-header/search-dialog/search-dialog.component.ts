import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { SearchContactsService } from '../search-contacts.service';

@Component({
  selector: 'search-dialog',
  templateUrl: './search-dialog.component.html',
  styleUrls: ['./search-dialog.component.scss'],
})
export class SearchDialogComponent implements OnInit {
  searchValue: string = '';
  constructor(
    private searchContactsService: SearchContactsService,
    public dialogRef: MatDialogRef<SearchDialogComponent>
  ) {}

  ngOnInit(): void {}

  search() {
    this.searchContactsService.announceSearch(this.searchValue.trim());
    this.searchContactsService.announceSearchReset(this.searchValue);
    this.dialogRef.close();
  }
}
